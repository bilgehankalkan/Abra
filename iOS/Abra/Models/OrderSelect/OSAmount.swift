
import UIKit

enum OSAmountMode {
    case weight
    case price
}

protocol OSAmountDelegate {
    func `continue`(amount: Int)
}

class OSAmountViewModel: NSObject {
    
    var delegate: OSAmountDelegate?
    var amountMode: OSAmountMode = .weight {
        didSet {
            switch amountMode {
            case .weight:
                stepper.maximumValue = 20.0
                
                switch OSBaseViewController.offerSelectMode {
                case .courier:
                    titleLabel.text = NSLocalizedString("How much does it weight?", comment: "")
                    actionButton.setTitle(NSLocalizedString("Find a courier", comment: ""), for: .normal)
                case .carry:
                    titleLabel.text = NSLocalizedString("How many kilograms can you carry?", comment: "")
                    actionButton.setTitle(NSLocalizedString("Continue", comment: ""), for: .normal)
                }
            case .price:
                stepper.maximumValue = 100.0
                titleLabel.text = NSLocalizedString("How much do you want to charge?", comment: "")
            }
            stepperValueChanged(stepper)
        }
    }
    
    @IBOutlet weak var titleLabel: UILabel!
    @IBOutlet weak var stepper: UIStepper!
    @IBOutlet weak var amountModeLabel: UILabel!
    @IBOutlet weak var amountLabel: UILabel!
    @IBOutlet weak var actionButton: UIButton!
    
    @IBAction func stepperValueChanged(_ sender: UIStepper) {
        if amountMode == .weight {
            amountLabel.text = "\(Int(sender.value))"
            
            if sender.value <= 1.0 {
                amountModeLabel.text = NSLocalizedString("Kilogram", comment: "")
            }
            else {
                amountModeLabel.text = NSLocalizedString("Kilograms", comment: "")
            }
        }
        else if amountMode == .price {
            amountLabel.text = "\(Int(sender.value))" + "₺"
            amountModeLabel.text = NSLocalizedString("Price", comment: "")
        }
    }
    
    @IBAction func `continue`(_ sender: UIButton) {
        delegate?.`continue`(amount: Int(stepper.value))
    }
    
}
