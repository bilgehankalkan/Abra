const appConfig = require("../utilities/config");
const rabbit = require('amqplib').connect(appConfig.pushQue.host);


module.exports = (message) => {
    rabbit.then(function (conn) {
        return conn.createChannel();
    }).then(function (chanel) {
        return chanel.assertQueue(appConfig.pushQue.channel).then(function (ok) {
            return chanel.sendToQueue(appConfig.pushQue.channel, new Buffer(JSON.stringify(message)));
        });
    }).catch((err) => {

    });
}