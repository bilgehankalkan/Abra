//
//  OSOriginLocationViewController.swift
//  Abra
//
//  Created by Hakan Eren on 17/02/2018.
//  Copyright © 2018 Hakan Eren. All rights reserved.
//

import UIKit

enum OSLocationMode {
    case origin
    case destination
}

protocol OSLocationDelegate {
    func selected(_ location: Location)
    func `continue`()
}

class OSLocationViewModel: NSObject {
    
    var delegate: OSLocationDelegate?
    var locationMode: OSLocationMode = .origin {
        didSet {
            switch locationMode {
            case .origin:
                backButton.isHidden = true
                locationTextField.placeholder = "Enter the origin location"
                
                switch OSBaseViewController.offerSelectMode {
                case .courier:
                    titleLabel.text = "Where would you send it from?"
                case .carry:
                    titleLabel.text = "Where are you leaving from?"
                }
            case .destination:
                backButton.isHidden = false
                locationTextField.placeholder = "Enter the destination location"
                
                switch OSBaseViewController.offerSelectMode {
                case .courier:
                    titleLabel.text = "Where would you send it to"
                case .carry:
                    titleLabel.text = "Where are you heading?"
                }
            }
            locationTextField.addToolbar(type: .closeInputView)
        }
    }

    @IBOutlet weak var backButton: UIButton!
    @IBOutlet weak var titleLabel: UILabel!
    @IBOutlet weak var locationTextField: UITextField!
    @IBOutlet weak var locationTableView: UITableView!
    
    @IBAction func `continue`(_ sender: UIButton) {
        delegate?.`continue`()
    }
    
}

class Location {
    
}

class OSLocationViewController: OSBaseViewController {
    
    var locationMode: OSLocationMode = .origin

    @IBOutlet weak var locationViewModel: OSLocationViewModel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        locationViewModel.delegate = self
        locationViewModel.locationMode = locationMode
    }
    
}

extension OSLocationViewController: OSLocationDelegate {
    
    func selected(_ location: Location) {
        switch locationMode {
        case .origin:       OSBaseViewController.offerSelect.originLocation = location; `continue`()
        case .destination:  OSBaseViewController.offerSelect.destinationLocation = location; `continue`()
        }
    }
    
    func `continue`() {
        switch locationMode {
        case .origin:
            showDate(for: .origin, from: self)
        case .destination:
            switch OSBaseViewController.offerSelectMode {
            case .courier:
                showAmount(for: .weight, from: self)
            case .carry:
                showDate(for: .destination, from: self)
            }
        }
    }
    
}
