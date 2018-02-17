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
    func `continue`(note: String)
}

class OSNoteViewModel: NSObject {
    
    var delegate: OSNoteDelegate?
    var noteMode: OSNoteMode = .about {
        didSet {
            switch noteMode {
            case .about:
                aboutTextField.placeholder = "Your message goes here"
                aboutTextField.addToolbar(type: .closeInputView)
                titleLabel.text = "Anything to add about your trip?"
            }
        }
    }
    
    @IBOutlet weak var titleLabel: UILabel!
    @IBOutlet weak var aboutTextField: UITextField!
    
    @IBAction func `continue`(_ sender: UIButton) {
        delegate?.`continue`(note: aboutTextField.text ?? "")
    }
    
}

class OSNoteViewController: OSBaseViewController {
    
    var noteMode: OSNoteMode = .about
    
    @IBOutlet weak var noteViewModel: OSNoteViewModel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        noteViewModel.delegate = self
        noteViewModel.noteMode = noteMode
    }
    
}

extension OSNoteViewController: OSNoteDelegate {
    
    func `continue`(note: String) {
        switch noteMode {
        case .about:
            OSBaseViewController.offerSelect.note = note
            showFinish(from: self)
        }
    }
    
}

extension UITextField {
    
    enum UITextFieldAddToolbarType {
        case closeInputView
    }
    
    func addToolbar(type: UITextFieldAddToolbarType) {
        let toolBar = UIToolbar()
        toolBar.barStyle = UIBarStyle.default
        toolBar.isTranslucent = true
        toolBar.sizeToFit()

        if type == .closeInputView {
            let doneButton = UIBarButtonItem(title: "Close", style: .done, target: self, action: #selector(closeKeyboard(_:)))
            toolBar.setItems([doneButton], animated: false)
            toolBar.isUserInteractionEnabled = true
        }

        inputAccessoryView = toolBar
    }
    
    @objc func closeKeyboard(_ sender: UIBarButtonItem) {
        resignFirstResponder()
    }
}

