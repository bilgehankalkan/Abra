//
//  Carry.swift
//  Abra
//
//  Created by Hakan Eren on 17/02/2018.
//  Copyright © 2018 Hakan Eren. All rights reserved.
//

struct CourierAddRequest {
    
    var ownerId = "5a8746500000000000000000"
    var origin = ""
    var originDate = ""
    var destination = ""
    var destinationDate = ""
    var weight = 0
    var instantBooking = false
    var price = 0
    var note = ""
    
}

import ObjectMapper
import ObjectMapper_Realm
import RealmSwift

class CourierAddResponse: Object, Mappable {
    
    @objc dynamic var identifier = ""
    @objc dynamic var dateCreated = ""
    @objc dynamic var name = ""
    @objc dynamic var ownerId = ""
    @objc dynamic var origin = ""
    @objc dynamic var originDate = ""
    @objc dynamic var destination = ""
    @objc dynamic var destinationDate = ""
    @objc dynamic var weight = 0
    @objc dynamic var instantBooking = false
    @objc dynamic var price = 0
    @objc dynamic var note = ""
    
    required convenience init?(map: Map) {
        self.init()
    }
    
    func mapping(map: Map) {
        identifier      <- map["_id"]
        dateCreated     <- map["dateCreated"]
        name            <- map["name"]
        ownerId         <- map["ownerId"]
        origin          <- map["origin"]
        originDate      <- map["originDate"]
        destination     <- map["destination"]
        destinationDate <- map["destinationDate"]
        weight          <- map["weight"]
        instantBooking  <- map["instantBooking"]
        price           <- map["price"]
        note            <- map["note"]
    }
    
}

class Courier: Object, Mappable {

    @objc dynamic var identifier = ""
    @objc dynamic var name = ""
    
    required convenience init?(map: Map) {
        self.init()
    }
    
    func mapping(map: Map) {
        identifier  <- map["_id"]
        name        <- map["name"]
    }
    
}
