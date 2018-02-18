//
//  OSWeightViewController.swift
//  Abra
//
//  Created by Hakan Eren on 17/02/2018.
//  Copyright © 2018 Hakan Eren. All rights reserved.
//

import UIKit

class OSAmountViewController: OSBaseViewController {
    
    var amountMode: OSAmountMode = .weight
    
    @IBOutlet weak var amountViewModel: OSAmountViewModel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        amountViewModel.delegate = self
        amountViewModel.amountMode = amountMode
    }
    
}

extension OSAmountViewController: OSAmountDelegate {
    
    func `continue`(amount: Int) {
        switch amountMode {
        case .weight:
            OSBaseViewController.offerSelect.weight = amount
            
            switch OSBaseViewController.offerSelectMode {
            case .courier:
                finishOS()
            case .carry:
                showSwitch(for: .instantBooking, from: self)
            }
        case .price:
            OSBaseViewController.offerSelect.price = amount
            showNote(for: .about, from: self)
        }
    }
    
}
