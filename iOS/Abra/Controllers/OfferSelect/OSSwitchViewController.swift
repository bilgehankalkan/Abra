//
//  OSInstantBookingViewController.swift
//  Abra
//
//  Created by Hakan Eren on 17/02/2018.
//  Copyright © 2018 Hakan Eren. All rights reserved.
//

import UIKit

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
        switch switchMode {
        case .instantBooking:
            OSBaseViewController.offerSelect.instantBooking = `switch`
            showAmount(for: .price, from: self)
        }
    }
    
}
