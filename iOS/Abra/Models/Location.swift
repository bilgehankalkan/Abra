//
//  Location.swift
//  Abra
//
//  Created by Hakan Eren on 17/02/2018.
//  Copyright © 2018 Hakan Eren. All rights reserved.
//

import ObjectMapper
import ObjectMapper_Realm
import RealmSwift

class Location: Object, Mappable {
    
    @objc dynamic var identifier = ""
    @objc dynamic var name = ""
    @objc dynamic var date = ""
    
    required convenience init?(map: Map) {
        self.init()
    }
    
    func mapping(map: Map) {
        identifier  <- map["_id"]
        name        <- map["name"]
        date        <- map["date"]
    }
    
}
