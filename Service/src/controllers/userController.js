const express = require("express");
const bodyParser = require("body-parser");
const guid = require("guid");
const linq = require("ex-js-linq");
const math = require("../utilities/math");
const courierModel = require("../models/courier");
const bookModel = require("../models/book");
const userModel = require("../models/user");
const locationModel = require("../models/location");
const tokenModel = require("../models/token");
const responseCode = require("../utilities/responseCode");
const response = require("../utilities/response");
const _dictionary = require("../localization/dictionary");
let router = express.Router();

router.use(bodyParser.urlencoded({ extended: true }));
router.use(bodyParser.json());

router.post("/login", (req, res) => {
    var dictionary = _dictionary(req);
    const query = {
        email: req.body.email,
        password: req.body.password
    };
    const options = {
        new: true,
        fields: {
            password: 0
        }
    }
    userModel.findOne(query, options).then((user) => {
        var token = guid.create();
        tokenModel.create({
            userId: user._id,
            token: token,
            dateCreated: Date.now(),
            isActive: true
        }).then((r) => {
            res.status(responseCode.OK)
                .send(user);
        }).catch((err) => {
            res.status(responseCode.SERVER_ERROR)
                .send();
        });

    }).catch((err) => {
        res.status(responseCode.SERVER_ERROR)
            .send();
    })
});

router.get("/:userId/logout", (req, res) => {
    var dictionary = _dictionary(req);
    var token = req.headers["authorization"];
    const query = {
        userId: req.params.userId,
        token: token
    };
    const update = {
        isActive: { "$set": false }
    };

    tokenModel.findOneAndUpdate(query, update)
        .then((doc) => {
            res.status(responseCode.OK)
                .send(response(responseCode.OK, ""));
        })
        .catch((err) => {
            res.status(responseCode.SERVER_ERROR)
                .send(response(responseCode.SERVER_ERROR, dictionary.errorMessages.systemError));
        })
});

function initCouriers(data, userId, req) {
    var userIdArray = [];
    var locationIdArray = [];
    return data
        .then((couriers) => {
            new linq(couriers).forEach((x) => {
                userIdArray.push(x.ownerId);
                locationIdArray.push(x.origin);
                locationIdArray.push(x.destination);
            });
            return couriers;
        })
        .then((couriers) => {
            return userModel.getListByIdArray(userIdArray, req)
                .then((users) => {
                    return {
                        users: users,
                        couriers: couriers
                    }
                });
        })
        .then((result) => {
            return locationModel.getListByIdArray(locationIdArray, req)
                .then((locations) => {
                    return {
                        users: result.users,
                        couriers: result.couriers,
                        locations: locations
                    }
                });
        })
        .then((result) => {
            var couriers = result.couriers;
            var locations = result.locations;
            var users = result.users;

            couriers = new linq(couriers).select((x) => {
                var owner = new linq(users).firstOrDefault((y) => {
                    return x.ownerId.toString() == y._id.toString();
                });
                var origin = new linq(locations).firstOrDefault((y) => {
                    return x.origin.toString() == y._id.toString();
                });
                var destination = new linq(locations).firstOrDefault((y) => {
                    return x.destination.toString() == y._id.toString();
                });
                return {
                    _id: x._id,
                    dateCreated: x.dateCreated,
                    owner: owner,
                    destination: {
                        _id: x.destination.toString(),
                        name: destination.name,
                        date: x.destinationDate
                    },
                    origin: {
                        _id: x.origin.toString(),
                        name: origin.name,
                        date: x.originDate
                    },
                    isMyOrder: (owner._id.toString() == userId),
                    weight: x.weight,
                    price: x.price,
                    avgRating: parseInt(math.random(1, 5)),
                    totalRating: parseInt(math.random(1, 100)),
                    instantBooking: x.instantBooking,
                }
            }).toArray();
            return couriers;
        })
}

router.get("/:userId/courier/book/current/:pageIndex/:pageSize", (req, res) => {
    initCouriers(bookModel.getCurrentsByUserId(req.params.userId, req.params.pageIndex, req.params.pageSize, req)
        .then((books) => {
            var courierIdArray = [];
            new linq(books).forEach((x) => {
                courierIdArray.push(x.courierId);
            });
            return courierModel.getListById(courierIdArray, req)
                .then((couriers) => {
                    return couriers;
                })
                .catch((err) => {
                    res.status(err.statusCode)
                        .send(response(err.statusCode, err.message));
                });
        }), req.params.userId, req)
        .then((couriers) => {
            res.status(responseCode.OK)
                .send(response(responseCode.OK, "", {
                    key: "locations",
                    value: couriers
                }));
        })
        .catch((err) => {
            res.status(err.statusCode)
                .send(response(err.statusCode, err.message));
        });
});

router.get("/:userId/courier/book/past/:pageIndex/:pageSize", (req, res) => {
    var userIdArray = [];
    var locationIdArray = [];
    initCouriers(bookModel.getPastsByUserId(req.params.userId, req.params.pageIndex, req.params.pageSize, req)
        .then((books) => {
            var courierIdArray = [];
            new linq(books).forEach((x) => {
                courierIdArray.push(x.courierId);
            });
            return courierModel.getListById(courierIdArray, req)
                .then((couriers) => {
                    return couriers;
                })
                .catch((err) => {
                    res.status(err.statusCode)
                        .send(response(err.statusCode, err.message));
                });
        }), req.params.userId, req)
        .then((couriers) => {
            res.status(responseCode.OK)
                .send(response(responseCode.OK, "", {
                    key: "locations",
                    value: couriers
                }));
        })
        .catch((err) => {
            res.status(err.statusCode)
                .send(response(err.statusCode, err.message));
        });
});

router.post("/", (req, res) => {
    var dictionary = _dictionary(req);
    var user = {
        dateCreated: Date.now(),
        name: req.body.name,
        surname: req.body.surname,
        email: req.body.email,
        password: req.body.password,
    };
    const query = {
        email: user.email
    };
    const projection = {
        _id: 1
    };
    userModel.findOne(query, projection)
        .then((doc) => {
            if (doc == null) {
                userModel.create(user)
                    .then((doc) => {
                        res.status(responseCode.OK)
                            .send(response(responseCode.OK, "", {
                                key: "user",
                                value: {
                                    _id: doc._id,
                                    name: doc.name,
                                    surname: doc.surname,
                                    email: doc.email
                                }
                            }));
                    })
                    .catch((err) => {
                        res.status(responseCode.SERVER_ERROR)
                            .send(response(responseCode.SERVER_ERROR, err));
                    });
            } else {
                res.status(responseCode.OK)
                    .send(response(responseCode.OK, dictionary.errorMessages.usedToEmail));
            }
        })
        .catch((err) => {
            res.status(responseCode.SERVER_ERROR)
                .send(response(responseCode.SERVER_ERROR, dictionary.errorMessages.systemError));
        });

});



module.exports = router;