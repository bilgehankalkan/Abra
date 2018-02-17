const chalk = require("chalk");
const loggerType = {
    CONSOLE: 0,
    FILE_DOCUMENT: 1
}

const consoleLogger = function () {

    // this.error = (msg) => {
    //     console.log(chalk.red(msg));
    // }

    // this.success = (msg) => {
    //     console.log(chalk.green(msg));
    // }

    // this.info = (msg) => {
    //     console.log(chalk.yellow(msg));
    // }

    return {
        info:(msg)=>{
            console.log(chalk.yellow(msg));
        },
        success:(msg)=>{
            console.log(chalk.green(msg));
        },
        error:(msg)=>{
            console.log(chalk.red(msg));
        }
    }
};


function init(type) {
    if (type == loggerType.CONSOLE) {
        return consoleLogger();
    } else if (type == loggerType.FILE_DOCUMENT) {
        throw "not implemented";
    }
}

module.exports = {
    init: init,
    types: loggerType
};