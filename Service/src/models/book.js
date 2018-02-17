const mongoose = require("mongoose");
const collections = require("../db/collectionNames");
const responseCode = require("../utilities/responseCode");
const _dictionary = require("../localization/dictionary");
const Schema = mongoose.Schema;
const objectId = Schema.Types.ObjectId;

const bookSchema = new Schema({
    // _id: objectId,
    userId: objectId,
    courierId: objectId,
    dateCreated: Date,
    userConfirm: Boolean,
    destinationDate: Date,
    courierConfirm: Boolean
}, { versionKey: false });

mongoose.model("book", bookSchema, collections.book);
var model = mongoose.model("book");

model.insert = (courierId, destinationDate, userId, req) => {
    var dictionary = _dictionary(req);
    return new Promise((res, rej) => {
        model.create({
            userId: userId,
            courierId: courierId,
            destinationDate: destinationDate,
            dateCreated: Date.now(),
        }).then(res)
            .catch((err) => {
                rej({
                    message: dictionary.errorMEssages.systemError,
                    statusCode: responseCode.SERVER_ERROR
                })
            })
    });
};

model.getListByUserId = (userId, pageIndex, pageSize, req) => {
    var dictionary = _dictionary(req);
    return new Promise((res, rej) => {
        const query = {
            userId: { $eq: userId }
        };
        model.find(query)
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
};

model.getCurrentsByUserId = (userId, pageIndex, pageSize, req) => {
    var dictionary = _dictionary(req);
    return new Promise((res, rej) => {
        const query = {
            $and: [
                {
                    $or: [
                        {
                            userId: { $eq: userId }
                        },
                        {
                            courierId: { $eq: userId }
                        }
                    ]
                },
                {
                    destinationDate: { $gte: Date.now() }
                }
            ]
        };
        model.find(query)
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
};

model.getPastsByUserId = (userId, pageIndex, pageSize, req) => {
    var dictionary = _dictionary(req);
    return new Promise((res, rej) => {
        const query = {
            $and: [
                {
                    $or: [
                        {
                            userId: { $eq: userId }
                        },
                        {
                            courierId: { $eq: userId }
                        }
                    ]
                },
                {
                    destinationDate: { $lt: Date.now() }
                }
            ]
        };
        model.find(query)
            .limit(parseInt(pageSize))
            .skip(parseInt(pageIndex) * parseInt(pageSize))
            .exec()
            .then(res)
            .catch((err) => {
                console.log(err);
                rej({
                    message: dictionary.errorMessages.systemError,
                    statusCode: responseCode.SERVER_ERROR
                });
            });
    });
};

module.exports = model;