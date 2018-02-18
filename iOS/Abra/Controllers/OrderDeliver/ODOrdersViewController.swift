//
//  OrdersViewController.swift
//  Abra
//
//  Created by Hakan Eren on 17/02/2018.
//  Copyright © 2018 Hakan Eren. All rights reserved.
//

import UIKit

class ODOrdersViewController: ODBaseViewController {
    
    @IBOutlet weak var orderViewModel: OrderViewModel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        orderViewModel.delegate = self
        orderViewModel.orderDeliveryMode = .courier
        navigationItem.titleView = orderViewModel.ordersSegmentedControl
    }
    
}

extension ODOrdersViewController: OrderViewModelDelegate {
    
    func selected(_ order: Order) {
        print(order)
    }
    
}
