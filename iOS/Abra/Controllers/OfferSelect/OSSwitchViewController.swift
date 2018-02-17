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
    var mode: OSSwitchMode = .instantBooking {
        didSet {
            switch mode {
            case .instantBooking:
                titleLabel.text = "Save time and let your clients book instantly!"
                subtitleLabel.text = "If not, you'll have to reply to every booking request yourself!"
                modeLabel.text = "Instant Booking"
            }
        }
    }
    
    @IBOutlet weak var titleLabel: UILabel!
    @IBOutlet weak var subtitleLabel: UILabel!
    @IBOutlet weak var modeLabel: UILabel!
    @IBOutlet weak var `switch`: UISwitch!
    
    @IBAction func `continue`(_ sender: UIButton) {
        delegate?.`continue`(switch: `switch`.isOn)
    }
    
}

class OSSwitchViewController: OSBaseViewController {
    
    var mode: OSSwitchMode = .instantBooking
    
    @IBOutlet weak var switchViewModel: OSSwitchViewModel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        switchViewModel.delegate = self
        switchViewModel.mode = mode
    }
    
}

extension OSSwitchViewController: OSSwitchDelegate {
    
    func `continue`(switch: Bool) {
        print(`switch`)
        switch mode {
        case .instantBooking:
            OSBaseViewController.offerSelect.instantBooking = `switch`
            showAmount(for: .price, from: self)
        }
    }
    
}
