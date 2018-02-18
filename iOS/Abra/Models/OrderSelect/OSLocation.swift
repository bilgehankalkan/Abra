
import UIKit

enum OSLocationMode {
    case origin
    case destination
}

protocol OSLocationViewModelDelegate {
    func selected(_ location: Location)
}

class OSLocationViewModel: NSObject {
    
    var delegate: OSLocationViewModelDelegate?
    var locationMode: OSLocationMode = .origin {
        didSet {
            switch locationMode {
            case .origin:
                backButton.isHidden = true
                locationTextField.placeholder = "Enter the origin location"
                
                switch OSBaseViewController.offerSelectMode {
                case .courier:
                    titleLabel.text = "Where would you like to send it from?"
                case .carry:
                    titleLabel.text = "Where are you leaving from?"
                }
            case .destination:
                backButton.isHidden = false
                locationTextField.placeholder = "Enter the destination location"
                
                switch OSBaseViewController.offerSelectMode {
                case .courier:
                    titleLabel.text = "Where would you like to send it to?"
                case .carry:
                    titleLabel.text = "Where are you heading?"
                }
            }
            locationTextField.addToolbar(type: .closeInputView)
        }
    }
    
    @IBOutlet weak var backButton: UIButton!
    @IBOutlet weak var titleLabel: UILabel!
    @IBOutlet weak var locationTextField: UITextField!
    @IBOutlet weak var locationsTableView: UITableView!
    @IBOutlet weak var loadingActivityIndicatorView: UIActivityIndicatorView!
    
    var locations = [Location]()
    
    var timer: Timer?
    
    @IBAction func search(_ sender: UITextField) {
        timer?.invalidate()
        timer = Timer.scheduledTimer(timeInterval: 0.3, target: self, selector: #selector(performSearch(_:)), userInfo: sender, repeats: false)
    }
    
    @objc func performSearch(_ sender: Timer) {
        if let textField = sender.userInfo as? UITextField {
            DispatchQueue.global(qos: .userInitiated).async { [unowned self] in
                self.locations.removeAll()
                DispatchQueue.main.async {
                    self.locationsTableView.reloadData()
                    self.locationsTableView.isHidden = true
                    self.loadingActivityIndicatorView.isHidden = false
                }
            }
            API.shared.location(search: textField.text ?? "", completion: {
                (locations: [Location]?, error: Error?) in
                DispatchQueue.global(qos: .userInitiated).async { [unowned self] in
                    if let locations = locations {
                        self.locations.append(contentsOf: locations)
                    }
                    DispatchQueue.main.async {
                        self.locationsTableView.reloadData()
                        self.locationsTableView.isHidden = self.locations.count == 0
                        self.loadingActivityIndicatorView.isHidden = true
                    }
                }
            })
        }
    }
    
}

extension OSLocationViewModel: UITableViewDataSource {
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return locations.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "Cell", for: indexPath)
        
        cell.textLabel?.text = locations[indexPath.item].name
        
        return cell
    }
    
}

extension OSLocationViewModel: UITableViewDelegate {
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: true)
        let location = locations[indexPath.item]
        locationTextField.text = location.name
        self.delegate?.selected(location)
    }
    
}

import ObjectMapper
import ObjectMapper_Realm
import RealmSwift

class Location: Object, Mappable {
    
    @objc dynamic var identifier = ""
    @objc dynamic var name = ""
    @objc dynamic var date = ""
    
    required convenience init?(map: Map) {
        self.init()
    }
    
    func mapping(map: Map) {
        identifier  <- map["_id"]
        name        <- map["name"]
        date        <- map["date"]
    }
    
}
