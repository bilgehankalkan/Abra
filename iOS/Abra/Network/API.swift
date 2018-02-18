
import Alamofire
import AlamofireObjectMapper

final class API {
    
    static let shared = API()
    
    fileprivate let baseUrl: String = "https://getir-hackathon.exlinetr.com/sandbox/"
    fileprivate let keyPath = "data"
    fileprivate let encoding: URLEncoding = .`default`
    fileprivate let headers: Dictionary<String, String>? = ["Content-Type" : "application/json",
                                                            "Authorization": "token=638B736C-C73B-4BB5-AC9C-3B1C08F84078",
                                                            "Accept-Language": NSLocale.preferredLanguages[0]]
    fileprivate let userID = "5a888e1ac550959c285e1208"
    
    func location(search name:String, completion: @escaping ([Location]?, Error?) -> Void) {
        request(location: .list(name)).responseArray(keyPath: keyPath) {
            (response: DataResponse<[Location]>) in
            if response.error != nil {
                completion(nil, response.error)
            }
            if let responseValue = response.result.value {
                completion(responseValue, nil)
            }
            else {
                completion(nil, response.result.error)
            }
        }
    }
    
    func courierList(by selection: OfferSelect, completion: @escaping ([Order]?, Error?) -> Void) {
        request(courier: .all(selection)).responseArray(keyPath: keyPath) {
            (response: DataResponse<[Order]>) in
            if response.error != nil {
                completion(nil, response.error)
            }
            if let responseValue = response.result.value {
                completion(responseValue, nil)
            }
            else {
                completion(nil, response.result.error)
            }
        }
    }
    
    func courierAdd(by selection: OfferSelect, completion: @escaping (CourierAddResponse?, Error?) -> Void) {
        request(courier: .new(selection)).responseObject(keyPath: keyPath) {
            (response: DataResponse<CourierAddResponse>) in
            if response.error != nil {
                completion(nil, response.error)
            }
            if let responseValue = response.result.value {
                completion(responseValue, nil)
            }
            else {
                completion(nil, response.result.error)
            }
        }
    }
    
    func orders(_ endpoint: ordersEndpoint, completion: @escaping ([Order]?, Error?) -> Void) {
        request(orders: endpoint).responseArray(keyPath: keyPath) {
            (response: DataResponse<[Order]>) in
            if response.error != nil {
                completion(nil, response.error)
            }
            if let responseValue = response.result.value {
                completion(responseValue, nil)
            }
            else {
                completion(nil, response.result.error)
            }
        }
    }
    
}

extension API {
    
    fileprivate func request(location endpoint: locationEndpoint) -> DataRequest {
        return Alamofire.request(baseUrl + endpoint.path,
                                 method: endpoint.method,
                                 parameters: endpoint.parameters,
                                 encoding: API.shared.encoding,
                                 headers: API.shared.headers).validate()
    }
    
    enum locationEndpoint {
        case list(String)
        
        var path: String {
            switch self {
            case .list:
                return "location/search/"
            }
        }
        
        var method: HTTPMethod {
            switch self {
            case .list:
                return .get
            }
        }
        
        var parameters: [String: Any]? {
            switch self {
            case .list(let query):
                return ["q" : query]
            }
        }
    }
    
}

extension API {
    
    fileprivate func request(courier endpoint: courierEndpoint) -> DataRequest {
        return Alamofire.request(baseUrl + endpoint.path,
                                 method: endpoint.method,
                                 parameters: endpoint.parameters,
                                 encoding: endpoint.encoding,
                                 headers: API.shared.headers).validate()
    }
    
    enum courierEndpoint {
        case all(OfferSelect)
        case new(OfferSelect)

        var path: String {
            switch self {
            case .all:
                return "courier/search/0/20/"
            case .new:
                return "courier/"
            }
        }
        
        var method: HTTPMethod {
            switch self {
            case .all:
                return .get
            case .new:
                return .post
            }
        }
        
        var parameters: [String: Any]? {
            switch self {
            case .all(let offerSelect):
                return ["userId": API.shared.userID,
                        "originLocation": (offerSelect.origin?.identifier)!,
                        "originDate": (offerSelect.origin?.date)!,
                        "destinationLocation": (offerSelect.origin?.identifier)!,
                        "weight": (offerSelect.weight)!]
            case .new(let offerSelect):
                return ["ownerId": API.shared.userID,
                        "origin" : (offerSelect.origin?.identifier)!,
                        "originDate": (offerSelect.origin?.date)!,
                        "destination": (offerSelect.destination?.identifier)!,
                        "destinationDate": (offerSelect.destination?.date)!,
                        "weight": (offerSelect.weight)!,
                        "instantBooking": (offerSelect.instantBooking)!,
                        "price": (offerSelect.price)!,
                        "note": (offerSelect.note)!]
            }
        }
        
        var encoding: ParameterEncoding {
            switch self {
            case .all:
                return URLEncoding.`default`
            case .new:
                return JSONEncoding.`default`
            }
        }
    }
    
}

extension API {
    
    fileprivate func request(orders endpoint: ordersEndpoint) -> DataRequest {
        return Alamofire.request(baseUrl + endpoint.path,
                                 method: endpoint.method,
                                 parameters: endpoint.parameters,
                                 encoding: API.shared.encoding,
                                 headers: API.shared.headers).validate()
    }
    
    enum ordersEndpoint {
        case current(OrderDeliveryMode)
        case past(OrderDeliveryMode)

        var path: String {
            switch self {
            case .current(let orderDeliveryMode):
                switch orderDeliveryMode {
                case .courier:
                    return "/user/" + API.shared.userID + "/courier/book/current/0/20"
                case .carry:
                    return "/user/" + API.shared.userID + "/carry/book/current/0/20"
                }
            case .past(let orderDeliveryMode):
                switch orderDeliveryMode {
                case .courier:
                    return "/user/" + API.shared.userID + "/courier/book/past/0/20"
                case .carry:
                    return "/user/" + API.shared.userID + "/carry/book/past/0/20"
                }
            }
        }
        
        var method: HTTPMethod {
            switch self {
            case .current:
                return .get
            case .past:
                return .get
            }
        }
        
        var parameters: [String: Any]? {
            return nil
        }
    }
    
}
