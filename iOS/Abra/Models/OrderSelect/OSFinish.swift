
import UIKit

protocol OSFinishDelegate {
    func `continue`()
}

class OSFinishViewModel: NSObject {
    
    var delegate: OSFinishDelegate?
    
    @IBAction func `continue`(_ sender: UIButton) {
        delegate?.`continue`()
    }
    
}
