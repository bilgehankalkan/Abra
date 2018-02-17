const mongoose = require("mongoose");
const collections = require("../db/collectionNames");
const responseCode = require("../utilities/responseCode");
const _dictionary = require("../localization/dictionary");
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
var model = mongoose.model("user");

model.getListByIdArray = (idArray, req) => {
    var dictionary = _dictionary(req);
    return new Promise((res, rej) => {
        const query = {
            _id: {
                $in: idArray
            }
        };
        model.find(query, { name: 1, surname: 1 })
            .then(res)
            .catch((err) => {
                rej({
                    message: dictionary.errorMessages.systemError,
                    statusCode: responseCode.BAD_REQUEST
                });
            });
    });
}

model.login = (email, password, req) => {
    var dictionary = _dictionary(req);
    return new Promise((res, rej) => {
        if (email == null || email == "") {
            rej({
                message: dictionary.errorMessages.systemError,
                statusCode: responseCode.SERVER_ERROR
            });
        } else if (password == null || password == "") {

        }
    });
    const query = {
        email: email,
        password: password
    };
    const options = {
        new: true,
        fields: {
            password: 0
        }
    }
    userModel.findOne(query, options).then((user) => {
        var token = guid.create();
        tokenModel.create({
            userId: user._id,
            token: token,
            dateCreated: Date.now(),
            isActive: true
        }).then((r) => {
            res.status(responseCode.OK)
                .send(user);
        }).catch((err) => {
            res.status(responseCode.SERVER_ERROR)
                .send();
        });

    }).catch((err) => {
        res.status(responseCode.SERVER_ERROR)
            .send();
    })
};

module.exports = model;