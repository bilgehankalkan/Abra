//
//  OSTimeViewController.swift
//  Abra
//
//  Created by Hakan Eren on 17/02/2018.
//  Copyright © 2018 Hakan Eren. All rights reserved.
//

import UIKit

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

