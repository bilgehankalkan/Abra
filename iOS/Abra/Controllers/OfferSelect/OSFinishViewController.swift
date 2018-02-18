
import UIKit

class OSFinishViewController: OSBaseViewController {
    
    @IBOutlet weak var finishViewModel: OSFinishViewModel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        finishViewModel.delegate = self
    }
    
}

extension OSFinishViewController: OSFinishDelegate {
    
    func `continue`() {
        finishOS()
    }
    
}
