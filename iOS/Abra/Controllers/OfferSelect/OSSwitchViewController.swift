
import UIKit

class OSSwitchViewController: OSBaseViewController {
    
    var switchMode: OSSwitchMode = .instantBooking
    
    @IBOutlet weak var switchViewModel: OSSwitchViewModel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        switchViewModel.delegate = self
        switchViewModel.switchMode = switchMode
    }
    
}

extension OSSwitchViewController: OSSwitchDelegate {
    
    func `continue`(switch: Bool) {
        switch switchMode {
        case .instantBooking:
            OSBaseViewController.offerSelect.instantBooking = `switch`
            showAmount(for: .price, from: self)
        }
    }
    
}
