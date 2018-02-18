
import UIKit

enum OSDateMode {
    case origin
    case destination
}

protocol OSDateDelegate {
    func `continue`(date: Date)
}

class OSDateViewModel: NSObject {
    
    var delegate: OSDateDelegate?
    var dateMode: OSDateMode = .origin {
        didSet {
            switch dateMode {
            case .origin:
                switch OSBaseViewController.offerSelectMode {
                case .courier:
                    titleLabel.text = "When would you like to send?"
                case .carry:
                    titleLabel.text = "When are you going?"
                }
            case .destination:
                titleLabel.text = "When are you arriving?"
            }
        }
    }
    
    @IBOutlet weak var titleLabel: UILabel!
    @IBOutlet weak var datePicker: UIDatePicker!
    
    @IBAction func `continue`(_ sender: UIButton) {
        delegate?.`continue`(date: datePicker.date)
    }
    
}

enum OSTimeMode {
    case origin
    case destination
}

protocol OSTimeDelegate {
    func `continue`(time: Date)
}

class OSTimeViewModel: NSObject {
    
    var delegate: OSTimeDelegate?
    var timeMode: OSTimeMode = .origin {
        didSet {
            switch timeMode {
            case .origin:
                switch OSBaseViewController.offerSelectMode {
                case .courier:
                    titleLabel.text = "What time would you like to send?"
                case .carry:
                    titleLabel.text = "What time are you going?"
                }
            case .destination:
                titleLabel.text = "What time are you arriving?"
            }
        }
    }
    
    @IBOutlet weak var titleLabel: UILabel!
    @IBOutlet weak var datePicker: UIDatePicker!
    
    @IBAction func `continue`(_ sender: UIButton) {
        delegate?.`continue`(time: datePicker.date)
    }
    
}
