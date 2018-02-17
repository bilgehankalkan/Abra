//
//  OSBaseViewController.swift
//  Abra
//
//  Created by Hakan Eren on 17/02/2018.
//  Copyright © 2018 Hakan Eren. All rights reserved.
//

import UIKit

struct OfferSelect {
    var originLocation: Location?
    var originDate: Date?
    var destinationLocation: Location?
    var destinationDate: Date?
    var weight: Double?
    var price: Int?
    var instantBooking: Bool?
    var note: String?
}

class OSBaseViewController: UIViewController {
    
    static var offerSelect: OfferSelect?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        navigationController?.setNavigationBarHidden(true, animated: false)
    }
    
}
