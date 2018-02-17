//
//  OSInstantBookingViewController.swift
//  Abra
//
//  Created by Hakan Eren on 17/02/2018.
//  Copyright © 2018 Hakan Eren. All rights reserved.
//

import UIKit

enum OSSwitchMode {
    case instantBooking
}

protocol OSSwitchDelegate {
    func selected(_ switch: Bool)
    func `continue`()
}

class OSSwitchViewModel: NSObject {
    
    var delegate: OSSwitchDelegate?
    
    @IBAction func `continue`(_ sender: UIButton) {
        delegate?.`continue`()
    }
    
}

class OSSwitchViewController: OSBaseViewController {
    
    var mode: OSSwitchMode = .instantBooking
    
    @IBOutlet weak var switchViewModel: OSSwitchViewModel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        switchViewModel.delegate = self
    }
    
}

extension OSSwitchViewController: OSSwitchDelegate {
    
    func selected(_ switch: Bool) {
        switch mode {
        case .instantBooking:   OSBaseViewController.offerSelect.instantBooking = `switch`
        }
    }
    
    func `continue`() {
        switch mode {
        case .instantBooking:   showAmount(for: .price, from: self)
        }
    }
    
}
