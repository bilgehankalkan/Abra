const express = require("express");
const db = require("./db");
const app = express();
const logger = require("./utilities/logger");
const consoleLogger = logger.init(logger.types.CONSOLE);
const config = require("./utilities/config");
const responseCode=require("./utilities/responseCode");
const response=require("./utilities/response");

const userController=require("./controllers/userController");
const courierController=require("./controllers/courierController");
const locationController=require("./controllers/locationController");

app.get("/",(req,res)=>{
    res.status(responseCode.NOT_FOUND)
        .send(response(responseCode.NOT_FOUND,""));
});

app.use("/user", userController);
app.use("/location",locationController);
app.use("/courier",courierController);

app.use((req,res)=>{
    res.status(responseCode.NOT_FOUND)
        .send(response(responseCode.NOT_FOUND,""));
});

app.listen(config.webServer.port, () => {
    consoleLogger.info("server listing port:" + config.webServer.port);
});

module.exports = app;