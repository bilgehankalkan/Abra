
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
                    titleLabel.text = NSLocalizedString("When would you like to send?", comment: "")
                case .carry:
                    titleLabel.text = NSLocalizedString("When are you going?", comment: "")
                }
            case .destination:
                titleLabel.text = NSLocalizedString("When are you arriving?", comment: "")
            }
            actionButton.setTitle(NSLocalizedString("Continue", comment: ""), for: .normal)
        }
    }
    
    @IBOutlet weak var titleLabel: UILabel!
    @IBOutlet weak var datePicker: UIDatePicker!
    @IBOutlet weak var actionButton: UIButton!
    
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
                    titleLabel.text = NSLocalizedString("What time would you like to send?", comment: "")
                case .carry:
                    titleLabel.text = NSLocalizedString("What time are you going?", comment: "")
                }
            case .destination:
                titleLabel.text = NSLocalizedString("What time are you arriving?", comment: "")
            }
            actionButton.setTitle(NSLocalizedString("Continue", comment: ""), for: .normal)
        }
    }
    
    @IBOutlet weak var titleLabel: UILabel!
    @IBOutlet weak var datePicker: UIDatePicker!
    @IBOutlet weak var actionButton: UIButton!
    
    @IBAction func `continue`(_ sender: UIButton) {
        delegate?.`continue`(time: datePicker.date)
    }
    
}
