//
//  OSNoteViewController.swift
//  Abra
//
//  Created by Hakan Eren on 17/02/2018.
//  Copyright © 2018 Hakan Eren. All rights reserved.
//

import UIKit

extension OSNoteViewController: OSNoteDelegate {
    
    func `continue`(note: String) {
        switch noteMode {
        case .about:
            OSBaseViewController.offerSelect.note = note
            showFinish(from: self)
        }
    }
    
}
