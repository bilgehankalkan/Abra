//
//  OSWeightViewController.swift
//  Abra
//
//  Created by Hakan Eren on 17/02/2018.
//  Copyright © 2018 Hakan Eren. All rights reserved.
//

import UIKit

enum OSAmountMode {
    case weight
    case price
}

protocol OSAmountDelegate {
    func selected(_ amount: Int)
    func `continue`()
}

class OSAmountViewModel: NSObject {
    
    var delegate: OSAmountDelegate?
    
    @IBAction func `continue`(_ sender: UIButton) {
        delegate?.`continue`()
    }
    
}

class OSAmountViewController: OSBaseViewController {
    
    var mode: OSAmountMode = .weight
    
    @IBOutlet weak var amountViewModel: OSAmountViewModel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        amountViewModel.delegate = self
    }
    
}

extension OSAmountViewController: OSAmountDelegate {
    
    func selected(_ amount: Int) {
        switch mode {
        case .weight:       OSBaseViewController.offerSelect.weight = amount
        case .price:        OSBaseViewController.offerSelect.price = amount
        }
    }
    
    func `continue`() {
        switch mode {
        case .weight:       showSwitch(for: .instantBooking, from: self)
        case .price:        showNote(for: .about, from: self)
        }
    }
    
}
