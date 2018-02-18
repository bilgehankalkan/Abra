
module.exports = {
    userCourierBookCurrent: (userId, pageSize, pageIndex) => {
        return "USER_" + userId + "COURIER_CURRENT_BOOK_" + pageIndex + "_" + pageSize;
    },
    userCourierBookPast: (userId, pageSize, pageIndex) => {
        return "USER_" + userId + "CURRENT_PAST_BOOK_" + pageIndex + "_" + pageSize;
    },
    userCaryyBookCurrent: (userId, pageSize, pageIndex) => {
        return "USER_" + userId + "CARYY_CURRENT_BOOK_" + pageIndex + "_" + pageSize;
    },
    userCaryyBookPast: (userId, pageSize, pageIndex) => {
        return "USER_" + userId + "CARYY_PAST_BOOK_" + pageIndex + "_" + pageSize;
    },
    userToken: (userId) => {
        return "USER_" + userId + "_TOKEN";
    },
    userPushNotificationToken: (userId) => {
        return "USER_" + userId + "_PUSH_NOTIFICATION_TOKENS";
    }
}