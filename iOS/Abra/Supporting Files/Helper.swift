//
//  Helper.swift
//  Abra
//
//  Created by Hakan Eren on 18/02/2018.
//  Copyright © 2018 Hakan Eren. All rights reserved.
//

import UIKit

extension UIColor {
    
    static var ratingStarGreen: UIColor {
        return UIColor(red:0.57, green:0.86, blue:0.35, alpha:1.0)
    }
    
    static var ratingStarOrange: UIColor {
        return UIColor(red:0.97, green:0.55, blue:0.29, alpha:1.0)
    }
    
    static var ratingStarRed: UIColor {
        return UIColor(red:0.85, green:0.00, blue:0.15, alpha:1.0)
    }
    
    static var seperatorGray: UIColor {
        return UIColor(red:0.88, green:0.88, blue:0.88, alpha:1.0)
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
