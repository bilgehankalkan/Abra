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
    weight: Number,
    price: Number

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
        } else if (courier.weight == null) {
            rej({
                message: dictionary.errorMessages.invalidLuggageCapacity,
                statusCode: responseCode.BAD_REQUEST
            });
        } else if (courier.weight < 0) {
            rej({
                message: dictionary.errorMessages.negativeBaggage,
                statusCode: responseCode.BAD_REQUEST
            });
        } else {
            model.create(courier)
                .then(res)
                .catch((err) => {
                    rej({
                        message: dictionary.errorMessages.systemError,
                        statusCode: responseCode.SERVER_ERROR
                    });
                });
        }
    });
};

function gets(query, projection, pageIndex, pageSize, req) {
    var dictionary = _dictionary(req);
    if (pageIndex == undefined) {
        pageIndex = 0;
    }
    if (pageSize == undefined) {
        pageSize = 20;
    }
    return new Promise((res, rej) => {
        model.find(query, projection)
            .limit(parseInt(pageSize))
            .skip(parseInt(pageIndex) * parseInt(pageSize))
            .exec()
            .then(res)
            .catch((err) => {
                rej({
                    message: dictionary.errorMessages.systemError,
                    statusCode: responseCode.SERVER_ERROR
                });
            });
    });
}

model.getList = (pageIndex, pageSize, req) => {
    return gets({}, {}, pageIndex, pageSize, req);
};
model.search = (originDate, destination, origin, weight, pageSize, pageIndex, req) => {
    const query = {
        $and: []
    };
    if (originDate != null) {
        query.$and.push({
            originDate: { $gte: originDate }
        });
    }
    if (destination != null) {
        query.$and.push({
            destination: destination
        });
    }
    if (origin != null) {
        query.$and.push({
            origin: origin
        });
    }
    if (weight != null) {
        weight.$and.push({
            weight: { $gte: weight }
        });
    }
    return gets(query, {}, pageIndex, pageSize, req);
}

module.exports = model;