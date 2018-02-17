const stringFormat = require("sprintf-js").sprintf;
const logger = require("../utilities/logger");
const consoleLogger = logger.init(logger.types.CONSOLE);

function init(req) {
    const acceptLanguage = req.headers["Accept-Language"];
    var dictionary = {};
    try {
        dictionary = require("./dictionary." + acceptLanguage + ".js");
        //stringFormat("./dictionary.{0}", [acceptLanguage])
    } catch (ex) {
        dictionary = require('./dictionary.tr-TR.js');
        consoleLogger.error(ex);
    } finally {
        return dictionary;
    }
}
module.exports = init;