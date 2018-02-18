const express = require("express");
const bodyParser = require("body-parser");
const responseCode = require("../utilities/responseCode");
const _dictionary = require("../localization/dictionary");
let router = express.Router();

router.use(bodyParser.urlencoded({ extended: true }));
router.use(bodyParser.json());

function init(req, res, next) {
    const token = req.headers["authorization"];
    var dictionary = _dictionary(req);
    if (token == null || token == "") {
        res.status(responseCode.UNAUTHENTICATED).send(dictionary.errorMessages.missingToken);
    }
    next();
}

module.exports = init;