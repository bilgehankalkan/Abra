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
    var mode: OSLocationMode = .origin {
        didSet {
            switch mode {
            case .origin:
                cancelButton.isHidden = false
                backButton.isHidden = true
                titleLabel.text = "Where are you leaving from?"
                locationTextField.placeholder = "Enter the origin location"
            case .destination:
                cancelButton.isHidden = true
                backButton.isHidden = false
                titleLabel.text = "Where are you heading?"
                locationTextField.placeholder = "Enter the destination location"
            }
            locationTextField.addToolbar(type: .closeInputView)
        }
    }

    @IBOutlet weak var cancelButton: UIButton!
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
    
    var mode: OSLocationMode = .origin

    @IBOutlet weak var locationViewModel: OSLocationViewModel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        locationViewModel.delegate = self
        locationViewModel.mode = mode
    }
    
}

extension OSLocationViewController: OSLocationDelegate {
    
    func selected(_ location: Location) {
        switch mode {
        case .origin:       OSBaseViewController.offerSelect.originLocation = location; `continue`()
        case .destination:  OSBaseViewController.offerSelect.destinationLocation = location; `continue`()
        }
    }
    
    func `continue`() {
        switch mode {
        case .origin:       showDate(for: .origin, from: self)
        case .destination:  showDate(for: .destination, from: self)
        }
    }
    
}
