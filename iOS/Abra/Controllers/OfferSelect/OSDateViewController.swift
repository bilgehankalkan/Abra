
import UIKit

class OSDateViewController: OSBaseViewController {
    
    var dateMode: OSDateMode = .origin
    
    @IBOutlet weak var dateViewModel: OSDateViewModel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        dateViewModel.delegate = self
        dateViewModel.dateMode = dateMode
    }
    
}

extension OSDateViewController: OSDateDelegate {
    
    func `continue`(date: Date) {
        switch dateMode {
        case .origin:
            OSBaseViewController.offerSelect.origin?.date = date.string()
            showTime(for: .origin, from: self)
        case .destination:
            OSBaseViewController.offerSelect.destination?.date = date.string()
            showTime(for: .destination, from: self)
        }
    }
    
}
