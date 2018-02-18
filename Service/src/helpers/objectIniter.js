const linq = require("ex-js-linq");
const math = require("../utilities/math");
const courierModel = require("../models/courier");
const bookModel = require("../models/book");
const userModel = require("../models/user");
const locationModel = require("../models/location");
const response = require("../utilities/response");
const responseCode = require("../utilities/responseCode");

module.exports.couriers = (data, userId, req) => {
    var userIdArray = [];
    var locationIdArray = [];
    return data
        .then((couriers) => {
            new linq(couriers).forEach((x) => {
                userIdArray.push(x.ownerId);
                locationIdArray.push(x.origin);
                locationIdArray.push(x.destination);
            });
            return couriers;
        })
        .then((couriers) => {
            return userModel.getListByIdArray(userIdArray, req)
                .then((users) => {
                    return {
                        users: users,
                        couriers: couriers
                    }
                });
        })
        .then((result) => {
            return locationModel.getListByIdArray(locationIdArray, req)
                .then((locations) => {
                    return {
                        users: result.users,
                        couriers: result.couriers,
                        locations: locations
                    }
                });
        })
        .then((result) => {
            var couriers = result.couriers;
            var locations = result.locations;
            var users = result.users;

            couriers = new linq(couriers).select((x) => {
                var owner = new linq(users).firstOrDefault((y) => {
                    return x.ownerId.toString() == y._id.toString();
                });
                var origin = new linq(locations).firstOrDefault((y) => {
                    return x.origin.toString() == y._id.toString();
                });
                var destination = new linq(locations).firstOrDefault((y) => {
                    return x.destination.toString() == y._id.toString();
                });
                return {
                    _id: x._id,
                    dateCreated: x.dateCreated.toISOString(),
                    owner: owner,
                    destination: {
                        _id: x.destination.toString(),
                        name: destination.name,
                        date: x.destinationDate.toISOString()
                    },
                    origin: {
                        _id: x.origin.toString(),
                        name: origin.name,
                        date: x.originDate.toISOString()
                    },
                    isMyOrder: (owner._id.toString() == userId),
                    weight: x.weight,
                    price: x.price,
                    avgRating: parseInt(math.random(1, 5)),
                    totalRating: parseInt(math.random(1, 100)),
                    instantBooking: x.instantBooking,
                }
            }).toArray();
            return couriers;
        });
}