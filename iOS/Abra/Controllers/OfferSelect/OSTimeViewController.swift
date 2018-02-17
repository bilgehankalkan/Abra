//
//  OSTimeViewController.swift
//  Abra
//
//  Created by Hakan Eren on 17/02/2018.
//  Copyright © 2018 Hakan Eren. All rights reserved.
//

import UIKit

enum OSTimeMode {
    case origin
    case destination
}

protocol OSTimeDelegate {
    func `continue`(time: Date)
}

class OSTimeViewModel: NSObject {
    
    var delegate: OSTimeDelegate?
    var timeMode: OSTimeMode = .origin {
        didSet {
            switch timeMode {
            case .origin:
                switch OSBaseViewController.offerSelectMode {
                case .courier:
                    titleLabel.text = "What time would you like to send?"
                case .carry:
                    titleLabel.text = "What time are you going?"
                }
            case .destination:
                titleLabel.text = "What time are you arriving?"
            }
        }
    }
    
    @IBOutlet weak var titleLabel: UILabel!
    @IBOutlet weak var datePicker: UIDatePicker!
    
    @IBAction func `continue`(_ sender: UIButton) {
        delegate?.`continue`(time: datePicker.date)
    }
    
}

class OSTimeViewController: OSBaseViewController {
    
    var timeMode: OSTimeMode = .origin
    
    @IBOutlet weak var timeViewModel: OSTimeViewModel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        timeViewModel.delegate = self
        timeViewModel.timeMode = timeMode
    }
    
}

extension OSTimeViewController: OSTimeDelegate {
    
    func `continue`(time: Date) {
        // TODO: MERGE DATE & TIME
        print(time)
        switch timeMode {
        case .origin:
            OSBaseViewController.offerSelect.originDate = time
            showLocation(for: .destination, from: self)
        case .destination:
            OSBaseViewController.offerSelect.destinationDate = time
            showAmount(for: .weight, from: self)
        }
    }
    
}

