//
//  OSNoteViewController.swift
//  Abra
//
//  Created by Hakan Eren on 17/02/2018.
//  Copyright © 2018 Hakan Eren. All rights reserved.
//

import UIKit

enum OSNoteMode {
    case about
}

protocol OSNoteDelegate {
    func selected(_ note: String)
    func `continue`()
}

class OSNoteViewModel: NSObject {
    
    var delegate: OSNoteDelegate?
    
    @IBAction func `continue`(_ sender: UIButton) {
        delegate?.`continue`()
    }
    
}

class OSNoteViewController: OSBaseViewController {
    
    var mode: OSNoteMode = .about
    
    @IBOutlet weak var noteViewModel: OSNoteViewModel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        noteViewModel.delegate = self
    }
    
}

extension OSNoteViewController: OSNoteDelegate {
    
    func selected(_ note: String) {
        switch mode {
        case .about:   OSBaseViewController.offerSelect.note = note
        }
    }
    
    func `continue`() {
        switch mode {
        case .about:   showFinish(from: self)
        }
    }
    
}

