
import UIKit

class OSLocationViewController: OSBaseViewController {
    
    var locationMode: OSLocationMode = .origin

    @IBOutlet weak var locationViewModel: OSLocationViewModel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        locationViewModel.delegate = self
        locationViewModel.locationMode = locationMode
    }
    
}

extension OSLocationViewController: OSLocationViewModelDelegate {
    
    func selected(_ location: Location) {
        switch locationMode {
        case .origin:       OSBaseViewController.offerSelect.origin = location; `continue`()
        case .destination:  OSBaseViewController.offerSelect.destination = location; `continue`()
        }
    }
    
    func `continue`() {
        switch locationMode {
        case .origin:
            showDate(for: .origin, from: self)
        case .destination:
            switch OSBaseViewController.offerSelectMode {
            case .courier:
                showAmount(for: .weight, from: self)
            case .carry:
                showDate(for: .destination, from: self)
            }
        }
    }
    
}
