function init(code, msg, data) {
    var model = {
        code: code,
        msg: msg,
    };
    if (data != undefined) {
        //model[data.key] = data.value;
        model.data = data.value;
    }
    return model;
}

module.exports = init;