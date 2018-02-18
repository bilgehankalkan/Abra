
import UIKit

extension Date {
    
    func string() -> String {
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "yyyy-MM-dd'T'HH:mm"
        return dateFormatter.string(from: self)
    }
    
}

extension String {
    
    func date() -> Date {
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "yyyy-MM-dd'T'HH:mm"
        return dateFormatter.date(from: self)!
    }
    
}

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
            let doneButton = UIBarButtonItem(title: NSLocalizedString("Close", comment: "Dismiss the keyboard"), style: .done, target: self, action: #selector(closeKeyboard(_:)))
            toolBar.setItems([doneButton], animated: false)
            toolBar.isUserInteractionEnabled = true
        }
        
        inputAccessoryView = toolBar
    }
    
    @objc func closeKeyboard(_ sender: UIBarButtonItem) {
        resignFirstResponder()
    }
}


extension UIViewController {
    
    func presentErrorAlertView(_ error: Error?) {
        let alertView = UIAlertController(title: NSLocalizedString("Ooops!", comment: "Used to show recognition of a mistake or minor accident, often as part of an apology."), message: error?.localizedDescription, preferredStyle: .alert)
        alertView.addAction(UIAlertAction(title: NSLocalizedString("Oki", comment: "Dismiss an alert"), style: .cancel, handler: nil))
        present(alertView, animated: true, completion: nil)
    }
    
}
