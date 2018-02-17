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
    func selected(_ time: Date)
    func `continue`()
}

class OSTimeViewModel: NSObject {
    
    var delegate: OSTimeDelegate?
    
    @IBAction func `continue`(_ sender: UIButton) {
        delegate?.`continue`()
    }
    
}

class OSTimeViewController: OSBaseViewController {
    
    var mode: OSTimeMode = .origin
    
    @IBOutlet weak var timeViewModel: OSTimeViewModel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        timeViewModel.delegate = self
    }
    
}

extension OSTimeViewController: OSTimeDelegate {
    
    func selected(_ time: Date) {
        switch mode {
        case .origin:       OSBaseViewController.offerSelect.originDate = time
        case .destination:  OSBaseViewController.offerSelect.destinationDate = time
        }
    }
    
    func `continue`() {
        switch mode {
        case .origin:       showLocation(for: .destination, from: self)
        case .destination:  showAmount(for: .weight, from: self)
        }
    }
    
}
