const redis = require("redis");
var bluebird = require("bluebird");
const appConfig = require("../utilities/config");

bluebird.promisifyAll(redis.RedisClient.prototype);
bluebird.promisifyAll(redis.Multi.prototype);

const redisClient = redis.createClient(appConfig.cacheServer.connectionString);

module.exports = redisClient;