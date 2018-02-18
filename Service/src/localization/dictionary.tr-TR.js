const errorMessages = {
    missingToken: "token eksik",
    unableToAuthenticate: "geçersiz token",
    usedToEmail: "bu mail adresi başka bir kullanıcı tarafından kullanılıyor",
    invalidUser: "geçersiz kullanıcı bilgisi",
    nullOrEmptyOriginDate: "ayrılış tarihini boş geçemezsiniz",
    nullOrEmptydestinationDate: "varış tarihini boş geçemezsiniz",
    nullOrEmptyDestination: "varış noktası seçmelisiniz",
    nullOrEmptyOrigin: "kalkış noktası seçmelisiniz",
    invalidLuggageCapacity:"geçersiz bagaj kapasitesi girdiniz",
    negativeBaggage:"negatif bir bagaj değeri giremezsiniz.",
    systemError: "beklenmedik bir hata oluştu"
};

module.exports = {
    errorMessages: errorMessages
}