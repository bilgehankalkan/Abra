
import UIKit

class OSTimeViewController: OSBaseViewController {
    
    var timeMode: OSTimeMode = .origin
    
    @IBOutlet weak var timeViewModel: OSTimeViewModel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        timeViewModel.delegate = self
        timeViewModel.timeMode = timeMode
    }
    
}

extension OSTimeViewController: OSTimeDelegate {
    
    func `continue`(time: Date) {
        // TODO: MERGE DATE & TIME
        switch timeMode {
        case .origin:
            OSBaseViewController.offerSelect.origin?.date = time.string()
            showLocation(for: .destination, from: self)
        case .destination:
            OSBaseViewController.offerSelect.destination?.date = time.string()
            showAmount(for: .weight, from: self)
        }
    }
    
}

