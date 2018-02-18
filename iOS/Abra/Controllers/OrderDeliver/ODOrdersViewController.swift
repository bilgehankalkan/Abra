
import UIKit

class ODOrdersViewController: ODBaseViewController {
    
    @IBOutlet weak var orderViewModel: OrderViewModel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        orderViewModel.delegate = self
        orderViewModel.orderDeliveryMode = .courier
        navigationItem.titleView = orderViewModel.ordersSegmentedControl
    }
    
}

extension ODOrdersViewController: OrderViewModelDelegate {
    
    func selected(_ order: Order) {
        print(order)
    }
    
}
