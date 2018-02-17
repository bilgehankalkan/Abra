//
//  OrdersViewController.swift
//  Abra
//
//  Created by Hakan Eren on 17/02/2018.
//  Copyright © 2018 Hakan Eren. All rights reserved.
//

import UIKit

class OrdersViewController: OFBaseViewController {
    
    override func viewDidLoad() {
        super.viewDidLoad()
        addSegmentedControl()
    }
    
    let segmentedControl = UISegmentedControl(items: ["Current", "Past"])

    func addSegmentedControl() {
        segmentedControl.selectedSegmentIndex = 0
        navigationItem.titleView = segmentedControl
    }

}
