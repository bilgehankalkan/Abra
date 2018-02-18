//
//  OSOriginDateViewController.swift
//  Abra
//
//  Created by Hakan Eren on 17/02/2018.
//  Copyright © 2018 Hakan Eren. All rights reserved.
//

import UIKit

class OSDateViewController: OSBaseViewController {
    
    var dateMode: OSDateMode = .origin
    
    @IBOutlet weak var dateViewModel: OSDateViewModel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        dateViewModel.delegate = self
        dateViewModel.dateMode = dateMode
    }
    
}

extension OSDateViewController: OSDateDelegate {
    
    func `continue`(date: Date) {
        switch dateMode {
        case .origin:
            OSBaseViewController.offerSelect.originDate = date
            showTime(for: .origin, from: self)
        case .destination:
            OSBaseViewController.offerSelect.destinationDate = date
            showTime(for: .destination, from: self)
        }
    }
    
}
