
import UIKit

class OSCarryViewController: OSBaseViewController {
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        startOS(mode: .carry) { [unowned self] in
            self.tabBarController?.selectedIndex = 1
        }
    }

}
