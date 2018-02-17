const mongoose = require("mongoose");
const collections = require("../db/collectionNames");
const _dictionary = require("../localization/dictionary");
const Schema = mongoose.Schema;
const objectId = Schema.Types.ObjectId;
const responseCode = require("../utilities/responseCode");

const courierSchema = new Schema({
    // _id: objectId,
    ownerId: objectId,
    dateCreated: Date,
    note: String,
    // ayrılış tarihi
    originDate: Date,
    // hedef tarihi
    destinationDate: Date,
    // gelen sipariş isteiğinin hemen onaylanması
    instantBooking: Boolean,
    // hedef
    destination: objectId,
    // başlangıç noktası
    origin: objectId,
    // bagaj kapasitesi
    luggageCapacity: Number

}, { versionKey: false });

mongoose.model("courier", courierSchema, collections.courier);

const model = mongoose.model("courier");

model.register = (courier, req) => {
    var dictionary = _dictionary(req);
    return new Promise((res, rej) => {
        if (courier.ownerId == null) {
            rej({
                message: dictionary.errorMessages.invalidUser,
                statusCode: responseCode.BAD_REQUEST
            });
        } else if (courier.originDate == null) {
            rej({
                message: dictionary.errorMessages.nullOrEmptyOriginDate,
                statusCode: responseCode.BAD_REQUEST
            })
        } else if (courier.destinationDate == null) {
            rej({
                message: dictionary.errorMessages.nullOrEmptydestinationDate,
                statusCode: responseCode.BAD_REQUEST
            });
        } else if (courier.destination == null) {
            rej({
                message: dictionary.errorMessages.nullOrEmptyDestination,
                statusCode: responseCode.BAD_REQUEST
            });
        } else if (courier.origin == null) {
            rej({
                message: dictionary.errorMessages.nullOrEmptyOrigin
            }, responseCode.BAD_REQUEST);
        } else if (courier.luggageCapacity == null) {
            rej({
                message: dictionary.errorMessages.invalidLuggageCapacity,
                statusCode: responseCode.BAD_REQUEST
            });
        } else if (courier.luggageCapacity < 0) {
            rej({
                message: dictionary.errorMessages.negativeBaggage,
                statusCode: responseCode.BAD_REQUEST
            });
        } else {
            model.create(courier)
                .then(res)
                .catch((err) => {
                    err.statusCode = responseCode.SERVER_ERROR;
                    rej(err);
                });
        }
    });
};

model.getList = (pageIndex, pageSize, req) => {
    model.find({})
        .skip(pageIndex * pageSize)
        .limit(pageSize);
};

module.exports = model;