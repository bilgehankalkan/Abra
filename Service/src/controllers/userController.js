const express = require("express");
const bodyParser = require("body-parser");
const guid = require("guid");
const userModel = require("../models/user");
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