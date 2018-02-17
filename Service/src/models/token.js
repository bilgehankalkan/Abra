const mongoose = require("mongoose");
const collections = require("../db/collectionNames");
const Schema = mongoose.Schema;
const objectId = Schema.Types.ObjectId;

const tokenSchema = new Schema({
    // _id: objectId,
    userId: objectId,
    token: String,
    dateCreated: Date,
    isActive: Boolean

}, { versionKey: false });

mongoose.model("token", tokenSchema, collections.token);

module.exports = mongoose.model("token");