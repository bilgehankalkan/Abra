const logger = require("../utilities/logger");
const consoleLogger = logger.init(logger.types.CONSOLE);

function init() {
    consoleLogger.info("project config init...");
    var config = {
        environmentType: process.env.environmentType || "dev",
        dbServer: {
            host: process.env.dbHost || undefined,
            user: process.env.dbUser || undefined,
            psswrd: process.env.dbPsswrd || undefined,
            port: process.env.dbPort || undefined,
            dbName: process.env.dbName || undefined,
        },
        webServer: {
            port: process.env.PORT || 8080,
        }
    };
    if (config.dbServer.host == undefined) {
        consoleLogger.error("db host");
    }
    if (config.dbServer.user == undefined) {
        consoleLogger.error("db user name");
    }
    if (config.dbServer.psswrd == undefined) {
        consoleLogger.error("db user psswrd");
    }
    if (config.dbServer.dbName == undefined) {
        consoleLogger.error("db name");
    }
    console.log(config);
    return config;
}
module.exports = init();