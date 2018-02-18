//
//  Finish.swift
//  Abra
//
//  Created by Hakan Eren on 18/02/2018.
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
