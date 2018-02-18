
import UIKit

class ODBaseViewController: UIViewController {
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setupBarButtons()
    }
    
    func setupBarButtons() {
        if let mainNavigationController = self.navigationController as? BaseNavigationController {
            navigationItem.setLeftBarButton(mainNavigationController.profileBarButtonItem, animated: true)
            navigationItem.setRightBarButton(mainNavigationController.inboxBarButtonItem, animated: true)
        }
    }

}
