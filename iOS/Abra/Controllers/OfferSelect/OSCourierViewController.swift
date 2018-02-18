
import UIKit

class OSCourierViewController: OSBaseViewController {
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        startOS(mode: .courier)  { [unowned self] in
            self.tabBarController?.selectedIndex = 1
        }
    }

}
