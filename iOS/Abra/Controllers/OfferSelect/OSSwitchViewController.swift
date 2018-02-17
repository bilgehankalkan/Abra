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
    func `continue`(switch: Bool)
}

class OSSwitchViewModel: NSObject {
    
    var delegate: OSSwitchDelegate?
    var switchMode: OSSwitchMode = .instantBooking {
        didSet {
            switch switchMode {
            case .instantBooking:
                titleLabel.text = "Save time and let your clients book instantly!"
                subtitleLabel.text = "If not, you'll have to reply to every booking request yourself!"
                switchModeLabel.text = "Instant Booking"
            }
        }
    }
    
    @IBOutlet weak var titleLabel: UILabel!
    @IBOutlet weak var subtitleLabel: UILabel!
    @IBOutlet weak var switchModeLabel: UILabel!
    @IBOutlet weak var `switch`: UISwitch!
    
    @IBAction func `continue`(_ sender: UIButton) {
        delegate?.`continue`(switch: `switch`.isOn)
    }
    
}

class OSSwitchViewController: OSBaseViewController {
    
    var switchMode: OSSwitchMode = .instantBooking
    
    @IBOutlet weak var switchViewModel: OSSwitchViewModel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        switchViewModel.delegate = self
        switchViewModel.switchMode = switchMode
    }
    
}

extension OSSwitchViewController: OSSwitchDelegate {
    
    func `continue`(switch: Bool) {
        print(`switch`)
        switch switchMode {
        case .instantBooking:
            OSBaseViewController.offerSelect.instantBooking = `switch`
            showAmount(for: .price, from: self)
        }
    }
    
}
