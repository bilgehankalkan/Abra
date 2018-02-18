
import UIKit

class ODDeliveriesViewController: ODBaseViewController {
    
    @IBOutlet weak var orderViewModel: OrderViewModel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        orderViewModel.delegate = self
        orderViewModel.orderDeliveryMode = .carry
        navigationItem.titleView = orderViewModel.ordersSegmentedControl
    }
    
}

extension ODDeliveriesViewController: OrderViewModelDelegate {
    
    func selected(_ order: Order) {
        print(order)
    }
    
}
