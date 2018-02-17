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
    func selected(_ date: Date)
    func `continue`()
}

class OSDateViewModel: NSObject {
    
    var delegate: OSDateDelegate?
    
    @IBAction func `continue`(_ sender: UIButton) {
        delegate?.`continue`()
    }
    
}

class OSDateViewController: OSBaseViewController {
    
    var mode: OSDateMode = .origin
    
    @IBOutlet weak var dateViewModel: OSDateViewModel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        dateViewModel.delegate = self
    }
    
}

extension OSDateViewController: OSDateDelegate {
    
    func selected(_ date: Date) {
        switch mode {
        case .origin:       OSBaseViewController.offerSelect.originDate = date
        case .destination:  OSBaseViewController.offerSelect.destinationDate = date
        }
    }
    
    func `continue`() {
        switch mode {
        case .origin:       showTime(for: .origin, from: self)
        case .destination:  showTime(for: .destination, from: self)
        }
    }
    
}
