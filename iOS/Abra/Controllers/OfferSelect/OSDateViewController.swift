//
//  OSOriginDateViewController.swift
//  Abra
//
//  Created by Hakan Eren on 17/02/2018.
//  Copyright © 2018 Hakan Eren. All rights reserved.
//

import UIKit

enum OSDateMode {
    case origin
    case destination
}

protocol OSDateDelegate {
    func `continue`(date: Date)
}

class OSDateViewModel: NSObject {
    
    var delegate: OSDateDelegate?
    var mode: OSDateMode = .origin {
        didSet {
            switch mode {
            case .origin:
                titleLabel.text = "When are you going?"
            case .destination:
                titleLabel.text = "When are you arriving?"
            }
        }
    }
    
    @IBOutlet weak var titleLabel: UILabel!
    @IBOutlet weak var datePicker: UIDatePicker!
    
    @IBAction func `continue`(_ sender: UIButton) {
        delegate?.`continue`(date: datePicker.date)
    }
    
}

class OSDateViewController: OSBaseViewController {
    
    var mode: OSDateMode = .origin
    
    @IBOutlet weak var dateViewModel: OSDateViewModel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        dateViewModel.delegate = self
        dateViewModel.mode = mode
    }
    
}

extension OSDateViewController: OSDateDelegate {
    
    func `continue`(date: Date) {
        print(date)
        switch mode {
        case .origin:
            OSBaseViewController.offerSelect.originDate = date
            showTime(for: .origin, from: self)
        case .destination:
            OSBaseViewController.offerSelect.destinationDate = date
            showTime(for: .destination, from: self)
        }
    }
    
}
