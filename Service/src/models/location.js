const mongoose = require("mongoose");
const collections = require("../db/collectionNames");
const responseCode = require("../utilities/responseCode");
const _dictionary = require("../localization/dictionary");
const Schema = mongoose.Schema;
const objectId = Schema.Types.ObjectId;

const locationSchema = new Schema({
    // _id: objectId,
    dateCreated: Date,
    country: String,
    name: String,
    lat: String,
    lng: String
}, { versionKey: false });

mongoose.model("location", locationSchema, collections.location);
const model = mongoose.model("location");

model.searchByNameWithStartWith = (q, req) => {
    var dictionary = _dictionary(req);
    return new Promise((res, rej) => {
        if (q == undefined) {
            rej({
                message: "",
                statusCode: responseCode.BAD_REQUEST
            });
        }
        const query = {
            name: {
                $regex: "^" + q + ".*",
                $options: "i"
            }
        }
        model.find(query)
            .then(res)
            .catch((err) => {
                rej({
                    message: dictionary.errorMessages.systemError,
                    statusCode: responseCode.SERVER_ERROR
                });
            });
    });
};

model.getListByIdArray = (idArray, req) => {
    var dictionary = _dictionary(req);
    return new Promise((res, rej) => {
        const query = {
            _id: {
                $in: idArray
            }
        };
        model.find(query, { name: 1 })
            .then(res)
            .catch((err) => {
                rej({
                    message: dictionary.errorMessages.systemError,
                    statusCode: responseCode.SERVER_ERROR
                });
            });
    });
}

module.exports = model;