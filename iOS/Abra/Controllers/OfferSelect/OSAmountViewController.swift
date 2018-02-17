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
    func `continue`(amount: Int)
}

class OSAmountViewModel: NSObject {
    
    var delegate: OSAmountDelegate?
    var mode: OSAmountMode = .weight {
        didSet {
            switch mode {
            case .weight:
                titleLabel.text = "How many kilograms can you carry?"
                stepper.maximumValue = 20.0
            case .price:
                titleLabel.text = "How much do you want to charge?"
                stepper.maximumValue = 100.0
            }
            stepperValueChanged(stepper)
        }
    }
    
    @IBOutlet weak var titleLabel: UILabel!
    @IBOutlet weak var stepper: UIStepper!
    @IBOutlet weak var modeLabel: UILabel!
    @IBOutlet weak var amountLabel: UILabel!
    
    @IBAction func stepperValueChanged(_ sender: UIStepper) {
        if mode == .weight {
            amountLabel.text = "\(Int(sender.value))"

            if sender.value <= 1.0 {
                modeLabel.text = "Kilogram"
            }
            else {
                modeLabel.text = "Kilograms"
            }
        }
        else if mode == .price {
            amountLabel.text = "\(Int(sender.value))" + "₺"
            modeLabel.text = "Price"
        }
    }
    
    @IBAction func `continue`(_ sender: UIButton) {
        delegate?.`continue`(amount: Int(stepper.value))
    }
    
}

class OSAmountViewController: OSBaseViewController {
    
    var mode: OSAmountMode = .weight
    
    @IBOutlet weak var amountViewModel: OSAmountViewModel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        amountViewModel.delegate = self
        amountViewModel.mode = mode
    }
    
}

extension OSAmountViewController: OSAmountDelegate {
    
    func `continue`(amount: Int) {
        print(amount)
        switch mode {
        case .weight:
            OSBaseViewController.offerSelect.weight = amount
            showSwitch(for: .instantBooking, from: self)
        case .price:
            OSBaseViewController.offerSelect.price = amount
            showNote(for: .about, from: self)
        }
    }
    
}
