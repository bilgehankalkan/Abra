
enum OrderDeliveryMode {
    case courier
    case carry
}

protocol OrderViewModelDelegate {
    func selected(_ order: Order)
}

class OrderViewModel: NSObject {
    
    var delegate: OrderViewModelDelegate?
    var orderDeliveryMode: OrderDeliveryMode = .courier {
        didSet {
            ordersSegmentedControl.selectedSegmentIndex = 0
            ordersSegmentedControl.addTarget(self, action: #selector(segmentChanged(_:)), for: .valueChanged)
            
            DispatchQueue.global(qos: .userInitiated).async { [unowned self] in
                self.currentOrders.removeAll()
                self.pastOrders.removeAll()
                DispatchQueue.main.async {
                    self.ordersTableView.reloadData()
                    self.ordersTableView.isHidden = true
                    self.loadingActivityIndicatorView.isHidden = false
                }
            }
            // TODO: MAKE CONCURRENT
            API.shared.orders(.current(orderDeliveryMode), completion: {
                (orders: [Order]?, error: Error?) in
                DispatchQueue.global(qos: .userInitiated).async { [unowned self] in
                    if let orders = orders {
                        self.currentOrders.append(contentsOf: orders)
                    }
                    DispatchQueue.main.async {
                        self.ordersTableView.reloadData()
                        self.ordersTableView.isHidden = false
                        self.loadingActivityIndicatorView.isHidden = true
                    }
                }
            })
            API.shared.orders(.past(orderDeliveryMode), completion: {
                (orders: [Order]?, error: Error?) in
                DispatchQueue.global(qos: .userInitiated).async { [unowned self] in
                    if let orders = orders {
                        self.pastOrders.append(contentsOf: orders)
                    }
                    DispatchQueue.main.async {
                        self.ordersTableView.reloadData()
                        self.ordersTableView.isHidden = false
                        self.loadingActivityIndicatorView.isHidden = true
                    }
                }
            })
        }
    }
    
    let ordersSegmentedControl = UISegmentedControl(items: [NSLocalizedString("Current", comment: "Current || Future, !Past of transactions"), NSLocalizedString("Past", comment: "History old of transactions")])
    
    @IBOutlet weak var ordersTableView: UITableView!
    @IBOutlet weak var loadingActivityIndicatorView: UIActivityIndicatorView!
    
    var currentOrders = [Order]()
    var pastOrders = [Order]()

    @objc func segmentChanged(_ sender: UISegmentedControl) {
        ordersTableView.reloadData()
    }
    
}

extension OrderViewModel: UITableViewDataSource {
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if ordersSegmentedControl.selectedSegmentIndex == 0 {
            return currentOrders.count
        }
        else if ordersSegmentedControl.selectedSegmentIndex == 1 {
            return pastOrders.count
        }
        return 0
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "Cell", for: indexPath) as! OrderTableViewCell
        
        if ordersSegmentedControl.selectedSegmentIndex == 0 {
            cell.order = currentOrders[indexPath.item]
        }
        else if ordersSegmentedControl.selectedSegmentIndex == 1 {
            cell.order = pastOrders[indexPath.item]
        }
        
        return cell
    }
    
}

extension OrderViewModel: UITableViewDelegate {
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: true)
        
        if ordersSegmentedControl.selectedSegmentIndex == 0 {
            delegate?.selected(currentOrders[indexPath.item])
        }
        else if ordersSegmentedControl.selectedSegmentIndex == 1 {
            delegate?.selected(pastOrders[indexPath.item])
        }
    }
    
}

import AlamofireImage

class OrderTableViewCell: UITableViewCell {
    
    var order = Order() {
        didSet {
            originTimeLabel.text = order.origin?.date
            originLocationLabel.text = order.origin?.name
            destinationTimeLabel.text = order.destination?.date
            destinationLocationLabel.text = order.destination?.name
            nameLabel.text = (order.owner?.name ?? "") + " " + (order.owner?.surname ?? "")
            ratingLabel.text = "\(order.avarageRating)" + " - " + "\(order.totalRating)" + NSLocalizedString(" rating", comment: "User's rate of transaction")
            if order.avarageRating >= 4.0 {
                ratingStarImageView.tintColor = UIColor.ratingStarGreen
            }
            else if order.avarageRating >= 3.0 {
                ratingStarImageView.tintColor = UIColor.ratingStarOrange
            }
            else {
                ratingStarImageView.tintColor = UIColor.ratingStarRed
            }
            
            if let orderOwner = order.owner, let ownerAvatar = URL(string: orderOwner.avatar) {
                avatarImageView.af_setImage(withURL: ownerAvatar, placeholderImage: UIImage(named: "avatar-placeholder"))
            }
            
            weightLabel.text = "\(order.weight)" + " kg"
            instantBookingImageView.isHidden = !order.instantBooking
            priceLabel.text = "\(order.price)" + "₺"
        }
    }
    
    @IBOutlet weak var originTimeLabel: UILabel!
    @IBOutlet weak var originLocationLabel: UILabel!
    @IBOutlet weak var destinationTimeLabel: UILabel!
    @IBOutlet weak var destinationLocationLabel: UILabel!
    @IBOutlet weak var avatarImageView: UIImageView!
    @IBOutlet weak var nameLabel: UILabel!
    @IBOutlet weak var ratingStarImageView: UIImageView!
    @IBOutlet weak var ratingLabel: UILabel!
    @IBOutlet weak var weightLabel: UILabel!
    @IBOutlet weak var instantBookingImageView: UIImageView!
    @IBOutlet weak var priceLabel: UILabel!
    
}

import ObjectMapper
import ObjectMapper_Realm
import RealmSwift

class Order: Object, Mappable {
    
    @objc dynamic var origin: Location?
    @objc dynamic var destination: Location?
    @objc dynamic var weight = 0
    @objc dynamic var instantBooking = false
    @objc dynamic var price = 0
    @objc dynamic var owner: User?
    @objc dynamic var avarageRating = 0.0
    @objc dynamic var totalRating = 0

    required convenience init?(map: Map) {
        self.init()
    }
    
    func mapping(map: Map) {
        origin          <- map["origin"]
        destination     <- map["destination"]
        weight          <- map["weight"]
        instantBooking  <- map["instantBooking"]
        price           <- map["price"]
        owner           <- map["owner"]
        avarageRating   <- map["avgRating"]
        totalRating     <- map["totalRating"]
    }
    
}

class User: Object, Mappable {
    
    @objc dynamic var identifier = ""
    @objc dynamic var name = ""
    @objc dynamic var surname = ""
    @objc dynamic var avatar = ""
    
    required convenience init?(map: Map) {
        self.init()
    }
    
    func mapping(map: Map) {
        identifier      <- map["_id"]
        name            <- map["name"]
        surname         <- map["surname"]
        avatar          <- map["avatar"]
    }
    
}

