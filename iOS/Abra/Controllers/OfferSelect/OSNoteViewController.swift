
import UIKit

extension OSNoteViewController: OSNoteDelegate {
    
    func `continue`(note: String) {
        switch noteMode {
        case .about:
            OSBaseViewController.offerSelect.note = note
            showFinish(from: self)
        }
    }
    
}
