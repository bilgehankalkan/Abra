const express = require("express");
const bodyParser = require("body-parser");
const mongoose = require("mongoose");
const courierModel = require("../models/courier");
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
            res.status(responseCode.OK)
                .send(response(responseCode.OK, "", {
                    key: "couriers",
                    value: couriers
                }));
        })
        .catch((err) => {
            res.status(responseCode.SERVER_ERROR)
                .send(response(responseCode.SERVER_ERROR, err));
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
        luggageCapacity: req.body.luggageCapacity
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

router.get("/search", (req, res) => {

});

module.exports = router;