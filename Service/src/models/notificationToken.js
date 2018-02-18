const mongoose = require("mongoose");
const collections = require("../db/collectionNames");
const responseCode = require("../utilities/responseCode");
const _dictionary = require("../localization/dictionary");
const Schema = mongoose.Schema;
const objectId = Schema.Types.ObjectId;

const notificationSchema = new Schema({
    userId: String,
    token: String,
    dateCreated: Date,
}, { versionKey: false });

mongoose.model("notificationToken", notificationSchema, collections.notificationToken);
var model = mongoose.model("notificationToken");

model.insert = (userId, token, req) => {
    var dictionary = _dictionary(req);
    return new Promise((res, rej) => {
        const data = {
            userId: userId,
            token: token,
            dateCreated: Date.now()
        };
        model.create(data)
            .then(res)
            .catch((err) => {
                rej({
                    message: dictionary.errorMessages.systemError,
                    statusCode: responseCode.SERVER_ERROR
                })
            });
    });
}

module.exports = model;