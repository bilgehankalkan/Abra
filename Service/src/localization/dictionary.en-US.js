const errorMessages = {
    missingToken: "missing token",
    unableToAuthenticate: "invalid token",
    usedToEmail: "email is already in use",
    invalidUser: "invalid username",
    nullOrEmptyOriginDate: "origin date is missing",
    nullOrEmptydestinationDate: "destination date is missing",
    nullOrEmptyDestination: "destination location is missing",
    nullOrEmptyOrigin: "origin location is missing",
    invalidLuggageCapacity:"invalid weight info",
    negativeBaggage:"negative weight info",
    
    systemError: "unexpected error occured"
};

module.exports = {
    errorMessages: errorMessages
}