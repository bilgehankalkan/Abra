const express = require("express");
const bodyParser = require("body-parser");
const locationModel = require("../models/location");
const response = require("../utilities/response");
const responseCode = require("../utilities/responseCode");
let router = express.Router();

router.use(bodyParser.urlencoded({ extended: true }));
router.use(bodyParser.json());

router.get("/:pageIndex/:pageSize", (req, res) => {
    locationModel.find({})
        .skip((req.params.pageIndex * req.params.pageSize))
        .limit(req.params.pageSize)
        .then((locations) => {
            res.status(responseCode.OK)
                .send(response(responseCode.OK, "", {
                    key: "locations",
                    value: locations
                }));
        }).catch((err) => {
            res.status(responseCode.SERVER_ERROR)
                .send(response(responseCode.SERVER_ERROR, err));
        });
});

router.get("/search", (req, res) => {
    locationModel.searchByNameWithStartWith(req.query.q, req)
        .then((docs) => {
            res.status(responseCode.OK)
                .send(response(responseCode.OK, "", {
                    key: "locations",
                    value: docs
                }));
        })
        .catch((err) => {
            res.status(err.statusCode)
                .send(response(err.statusCode, err.message));
        });
});

module.exports = router;