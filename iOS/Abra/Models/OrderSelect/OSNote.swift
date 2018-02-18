
import UIKit

enum OSNoteMode {
    case about
}

protocol OSNoteDelegate {
    func `continue`(note: String)
}

class OSNoteViewModel: NSObject {
    
    var delegate: OSNoteDelegate?
    var noteMode: OSNoteMode = .about {
        didSet {
            switch noteMode {
            case .about:
                aboutTextField.placeholder = "Your message goes here"
                aboutTextField.addToolbar(type: .closeInputView)
                titleLabel.text = "Anything to add about your trip?"
            }
        }
    }
    
    @IBOutlet weak var titleLabel: UILabel!
    @IBOutlet weak var aboutTextField: UITextField!
    
    @IBAction func `continue`(_ sender: UIButton) {
        delegate?.`continue`(note: aboutTextField.text ?? "")
    }
    
}

class OSNoteViewController: OSBaseViewController {
    
    var noteMode: OSNoteMode = .about
    
    @IBOutlet weak var noteViewModel: OSNoteViewModel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        noteViewModel.delegate = self
        noteViewModel.noteMode = noteMode
    }
    
}
