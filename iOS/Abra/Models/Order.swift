//
//  Order.swift
//  Abra
//
//  Created by Hakan Eren on 18/02/2018.
//  Copyright © 2018 Hakan Eren. All rights reserved.
//

import ObjectMapper
import ObjectMapper_Realm
import RealmSwift

class Order: Object, Mappable {
    
    @objc dynamic var origin: Location?
    @objc dynamic var destination: Location?
    @objc dynamic var weight = 0
    @objc dynamic var instantBooking = false
    @objc dynamic var price = 0
    @objc dynamic var owner: User?
    @objc dynamic var avarageRating = 0.0
    @objc dynamic var totalRating = 0

    required convenience init?(map: Map) {
        self.init()
    }
    
    func mapping(map: Map) {
        origin          <- map["origin"]
        destination     <- map["destination"]
        weight          <- map["weight"]
        instantBooking  <- map["instantBooking"]
        price           <- map["price"]
        owner           <- map["owner"]
        avarageRating   <- map["avgRating"]
        totalRating     <- map["totalRating"]
    }
    
}

class User: Object, Mappable {
    
    @objc dynamic var identifier = ""
    @objc dynamic var name = ""
    @objc dynamic var surname = ""
    @objc dynamic var avatar = ""
    
    required convenience init?(map: Map) {
        self.init()
    }
    
    func mapping(map: Map) {
        identifier      <- map["_id"]
        name            <- map["name"]
        surname         <- map["surname"]
        avatar          <- map["avatar"]
    }
    
}

