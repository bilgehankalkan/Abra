
import UIKit

protocol OSFinishDelegate {
    func `continue`()
}

class OSFinishViewModel: NSObject {
    
    var delegate: OSFinishDelegate? {
        didSet {
            titleLabel.text = NSLocalizedString("Post your trip and you're done!", comment: "")
            actionButton.setTitle(NSLocalizedString("Post a trip", comment: ""), for: .normal)
        }
    }
    @IBOutlet weak var titleLabel: UILabel!
    @IBOutlet weak var actionButton: UIButton!
    
    @IBAction func `continue`(_ sender: UIButton) {
        delegate?.`continue`()
    }
    
}
