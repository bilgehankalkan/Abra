
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
                    titleLabel.text = "How much does it weight?"
                    actionButton .setTitle("Find a courier", for: .normal)
                case .carry:
                    titleLabel.text = "How many kilograms can you carry?"
                    actionButton .setTitle("Continue", for: .normal)
                }
            case .price:
                stepper.maximumValue = 100.0
                titleLabel.text = "How much do you want to charge?"
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
                amountModeLabel.text = "Kilogram"
            }
            else {
                amountModeLabel.text = "Kilograms"
            }
        }
        else if amountMode == .price {
            amountLabel.text = "\(Int(sender.value))" + "₺"
            amountModeLabel.text = "Price"
        }
    }
    
    @IBAction func `continue`(_ sender: UIButton) {
        delegate?.`continue`(amount: Int(stepper.value))
    }
    
}
