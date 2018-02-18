//
//  DeliveriesViewController.swift
//  Abra
//
//  Created by Hakan Eren on 18/02/2018.
//  Copyright © 2018 Hakan Eren. All rights reserved.
//

import UIKit

class ODDeliveriesViewController: ODBaseViewController {
    
    @IBOutlet weak var orderViewModel: OrderViewModel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        orderViewModel.delegate = self
        orderViewModel.orderDeliveryMode = .carry
        navigationItem.titleView = orderViewModel.ordersSegmentedControl
    }
    
}

extension ODDeliveriesViewController: OrderViewModelDelegate {
    
    func selected(_ order: Order) {
        print(order)
    }
    
}
