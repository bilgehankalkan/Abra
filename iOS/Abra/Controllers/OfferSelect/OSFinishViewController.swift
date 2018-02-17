//
//  OSCarryViewController.swift
//  Abra
//
//  Created by Hakan Eren on 17/02/2018.
//  Copyright © 2018 Hakan Eren. All rights reserved.
//

import UIKit

protocol OSFinishDelegate {
    func `continue`()
}

class OSFinishViewModel: NSObject {
    
    var delegate: OSFinishDelegate?
    
    @IBAction func `continue`(_ sender: UIButton) {
        delegate?.`continue`()
    }
    
}

class OSFinishViewController: OSBaseViewController {
    
    @IBOutlet weak var finishViewModel: OSFinishViewModel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        finishViewModel.delegate = self
    }
    
}

extension OSFinishViewController: OSFinishDelegate {
    
    func `continue`() {
        finishOS()
    }
    
}
