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
    var mode: OSNoteMode = .about {
        didSet {
            switch mode {
            case .about:
                titleLabel.text = "Anything to add about your trip?"
                aboutTextField.placeholder = "Your message goes here"
                aboutTextField.addToolbar(type: .closeInputView)
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
    
    var mode: OSNoteMode = .about
    
    @IBOutlet weak var noteViewModel: OSNoteViewModel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        noteViewModel.delegate = self
        noteViewModel.mode = mode
    }
    
}

extension OSNoteViewController: OSNoteDelegate {
    
    func `continue`(note: String) {
        switch mode {
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

