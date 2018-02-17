const mongoose = require("mongoose");
const collections = require("../db/collectionNames");
const Schema = mongoose.Schema;
const objectId = Schema.Types.ObjectId;

const userSchema = new Schema({
    // _id: objectId,
    name: String,
    surname: String,
    email: String,
    password: String,
    dateCreated: Date
}, { versionKey: false });

mongoose.model("user", userSchema, collections.users);

module.exports = mongoose.model("user");