const mongoose = require("mongoose");
const stringFormat = require("sprintf-js").sprintf;
const appConfig = require("../utilities/config");
const logger = require("../utilities/logger");

const consoleLogger = logger.init(logger.types.CONSOLE);

function createConnectionString() {
    // return stringFormat("mongodb://{0}:{1}@{2}:{3}/{4}",[
    //     appConfig.dbServer.user,
    //     appConfig.dbServer.psswrd,
    //     appConfig.dbServer.host,
    //     appConfig.dbServer.port,
    //     appConfig.dbServer.dbName]
    // );

    return "mongodb://" + appConfig.dbServer.host + ":" + appConfig.dbServer.port+ "/" + appConfig.dbServer.dbName;
    return "mongodb://" + appConfig.dbServer.user + ":" + appConfig.dbServer.psswrd + "@" + appConfig.dbServer.host + ":" + appConfig.dbServer.port + "/" + appConfig.dbServer.dbName;
}

mongoose.connect(createConnectionString(), (err) => {
    if (!err) {
        consoleLogger.success("connected");
    } else {
        consoleLogger.error(err);
    }
});