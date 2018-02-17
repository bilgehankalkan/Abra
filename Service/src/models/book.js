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
    courierConfirm: Boolean
}, { versionKey: false });

mongoose.model("book", bookSchema, collections.book);
var model = mongoose.model("book");

model.insert = (courierId, userId, req) => {
    var dictionary = _dictionary(req);
    return new Promise((res, rej) => {
        model.create({
            userId: userId,
            courierId: courierId,
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
            .limit(pageSize)
            .skip(pageIndex * pageSize)
            .exec()
            .then(res)
            .catch((err) => {
                rej({
                    message: dictionary.errorMEssages.systemError,
                    statusCode: responseCode.SERVER_ERROR
                })
            })
    });
};

module.exports = model;