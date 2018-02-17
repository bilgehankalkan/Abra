const express = require("express");
const bodyParser = require("body-parser");
const mongoose = require("mongoose");
const linq = require("ex-js-linq");
const math = require("../utilities/math");
const courierModel = require("../models/courier");
const bookModel = require("../models/book");
const userModel = require("../models/user");
const locationModel = require("../models/location");
const response = require("../utilities/response");
const responseCode = require("../utilities/responseCode");
let router = express.Router();

const Schema = mongoose.Schema;
const objectId = Schema.Types.ObjectId;

router.use(bodyParser.urlencoded({ extended: true }));
router.use(bodyParser.json());

router.get("/:pageIndex/:pageSize", (req, res) => {
    courierModel.getList(req.params.pageIndex, req.params.pageSize, req)
        .then((couriers) => {
            var userIdArray = [];
            var locationIdArray = [];
            new linq(couriers).forEach((x) => {
                userIdArray.push(x.ownerId);
                locationIdArray.push(x.origin);
                locationIdArray.push(x.destination);
            });
            return {
                userIdArray: userIdArray,
                locationIdArray: locationIdArray,
                couriers: couriers
            }
        }).then((result) => {
            userModel.getListByIdArray(result.userIdArray, req)
                .then((users) => {
                    var couriers = new linq(result.couriers)
                        .select((x) => {
                            var owner = new linq(users).firstOrDefault((y) => {
                                return x.ownerId.toString() == y._id.toString();
                            });
                            return {
                                _id: x._id,
                                dateCreated: x.dateCreated,
                                owner: owner,
                                destination: {
                                    _id: x.destination.toString(),
                                    date: x.destinationDate
                                },
                                origin: {
                                    _id: x.origin.toString(),
                                    date: x.originDate
                                },
                                instantBooking: x.instantBooking,
                            }
                        }).toArray();
                    return couriers;
                })
                .then((couriers) => {
                    locationModel.getListByIdArray(result.locationIdArray, req)
                        .then((locations) => {
                            couriers = new linq(couriers)
                                .select((x) => {
                                    var origin = new linq(locations).firstOrDefault((y) => {
                                        return x.origin._id.toString() == y._id.toString();
                                    });
                                    var destination = new linq(locations).firstOrDefault((y) => {
                                        return x.destination._id.toString() == y._id.toString();
                                    });
                                    return {
                                        _id: x._id,
                                        dateCreated: x.dateCreated,
                                        owner: x.owner,
                                        origin: {
                                            name: origin.name,
                                            date: x.origin.date
                                        },
                                        destination: {
                                            name: destination.name,
                                            date: x.destination.date
                                        },
                                        instantBooking: x.instantBooking
                                    }
                                }).toArray();
                            return couriers;
                        })
                        .then((couriers) => {
                            res.status(responseCode.OK)
                                .send(response(responseCode.OK, "", {
                                    key: "couriers",
                                    value: couriers
                                }));
                        })
                        .catch((err) => {
                            res.status(err.statusCode)
                                .send(response(err.statusCode, err.message));
                        });
                })
                .catch((err) => {
                    res.status(err.statusCode)
                        .send(response(err.statusCode, err.message));
                });
        })
        .catch((err) => {
            res.status(err.statusCode)
                .send(response(err.statusCode, err.message));
        });
});

router.post("/", (req, res) => {
    var courier = {
        dateCreated: Date.now(),
        destinationDate: new Date(req.body.destinationDate),
        originDate: new Date(req.body.originDate),
        ownerId: req.body.ownerId,
        note: req.body.note,
        instantBooking: req.body.instantBooking,
        destination: req.body.destination,
        origin: req.body.origin,
        weight: req.body.weight,
        price: req.body.price
    };
    courierModel.register(courier, req)
        .then((doc) => {
            res.status(responseCode.CREATED)
                .send(response(responseCode.CREATED, "", {
                    key: "courier",
                    value: doc
                }));
        })
        .catch((err) => {
            res.status(err.statusCode)
                .send(response(err.statusCode, err.message));
        });
});

router.get("/search/:pageIndex/:pageSize", (req, res) => {
    courierModel.search(new Date(req.query.originDate), req.query.destination, req.query.origin, req.query.weight, req.params.pageSize, req.paramspageIndex, req)
        .then((couriers) => {
            var userIdArray = [];
            var locationIdArray = [];
            new linq(couriers).forEach((x) => {
                userIdArray.push(x.ownerId);
                locationIdArray.push(x.origin);
                locationIdArray.push(x.destination);
            });
            return {
                userIdArray: userIdArray,
                locationIdArray: locationIdArray,
                couriers: couriers
            }
        }).then((result) => {
            userModel.getListByIdArray(result.userIdArray, req)
                .then((users) => {
                    var couriers = new linq(result.couriers)
                        .select((x) => {
                            var owner = new linq(users).firstOrDefault((y) => {
                                return x.ownerId.toString() == y._id.toString();
                            });
                            return {
                                _id: x._id,
                                dateCreated: x.dateCreated,
                                owner: owner,
                                destination: {
                                    _id: x.destination.toString(),
                                    date: x.destinationDate
                                },
                                origin: {
                                    _id: x.origin.toString(),
                                    date: x.originDate
                                },
                                price: x.price,
                                weight:x.weight,
                                rating: parseInt(math.random(1, 5)),
                                totalComment: parseInt(math.random(1, 100)),
                                instantBooking: x.instantBooking,
                            }
                        }).toArray();
                    return couriers;
                })
                .then((couriers) => {
                    locationModel.getListByIdArray(result.locationIdArray, req)
                        .then((locations) => {
                            couriers = new linq(couriers)
                                .select((x) => {
                                    var origin = new linq(locations).firstOrDefault((y) => {
                                        return x.origin._id.toString() == y._id.toString();
                                    });
                                    var destination = new linq(locations).firstOrDefault((y) => {
                                        return x.destination._id.toString() == y._id.toString();
                                    });
                                    return {
                                        _id: x._id,
                                        dateCreated: x.dateCreated,
                                        owner: x.owner,
                                        origin: {
                                            name: origin.name,
                                            date: x.origin.date
                                        },
                                        destination: {
                                            name: destination.name,
                                            date: x.destination.date
                                        },
                                        weight:x.weight,
                                        rating: x.rating,
                                        totalComment: x.totalComment,
                                        price: x.price,
                                        instantBooking: x.instantBooking
                                    }
                                }).toArray();
                            return couriers;
                        })
                        .then((couriers) => {
                            res.status(responseCode.OK)
                                .send(response(responseCode.OK, "", {
                                    key: "couriers",
                                    value: couriers
                                }));
                        })
                        .catch((err) => {
                            res.status(err.statusCode)
                                .send(response(err.statusCode, err.message));
                        });
                })
                .catch((err) => {
                    res.status(err.statusCode)
                        .send(response(err.statusCode, err.message));
                });
        })
        .catch((err) => {
            res.status(err.statusCode)
                .send(response(err.statusCode, err.message));
        });
});

router.post("/:userId/book", (req, res) => {
    courierModel.getDestinationDateById(req.body.courierId, req)
        .then((courier) => {
            return bookModel.insert(req.body.courierId, courier.destinationDate, req.params.userId, req)
                .then((doc) => {
                    return doc;
                })
                .catch((err) => {
                    res.status(err.statusCode)
                        .send(response(err.statusCode, err.message));
                });
        }).then((doc) => {
            res.status(responseCode.CREATED)
                .send(response(responseCode.CREATED, ""));
        })
        .catch((err) => {
            res.status(err.statusCode)
                .send(response(err.statusCode, err.message));
        });
});

module.exports = router;