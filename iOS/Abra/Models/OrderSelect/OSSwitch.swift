
import UIKit

enum OSSwitchMode {
    case instantBooking
}

protocol OSSwitchDelegate {
    func `continue`(switch: Bool)
}

class OSSwitchViewModel: NSObject {
    
    var delegate: OSSwitchDelegate?
    var switchMode: OSSwitchMode = .instantBooking {
        didSet {
            switch switchMode {
            case .instantBooking:
                titleLabel.text = NSLocalizedString("Save time and let your clients book instantly!", comment: "")
                subtitleLabel.text = NSLocalizedString("If not, you'll have to reply to every booking request yourself!", comment: "")
                switchModeLabel.text = NSLocalizedString("Instant Booking", comment: "")
            }
            actionButton.setTitle(NSLocalizedString("Continue", comment: ""), for: .normal)
        }
    }
    
    @IBOutlet weak var titleLabel: UILabel!
    @IBOutlet weak var subtitleLabel: UILabel!
    @IBOutlet weak var switchModeLabel: UILabel!
    @IBOutlet weak var `switch`: UISwitch!
    @IBOutlet weak var actionButton: UIButton!
    
    @IBAction func `continue`(_ sender: UIButton) {
        delegate?.`continue`(switch: `switch`.isOn)
    }
    
}
