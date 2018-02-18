
import Alamofire
import AlamofireObjectMapper

final class API {
    
    static let sharedManager = API()
    
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
    
    func courierList(pageIndex: Int, pageSize: Int, completion: @escaping ([Courier]?, Error?) -> Void) {
        request(courierList: .all(pageIndex, pageSize)).responseArray(keyPath: keyPath) {
            (response: DataResponse<[Courier]>) in
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
    
    func courrier(add endpoint: courierAddEndpoint, completion: @escaping (CourierAddResponse?, Error?) -> Void) {
        request(courierAdd: endpoint).responseObject(keyPath: keyPath) {
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
                                 encoding: API.sharedManager.encoding,
                                 headers: API.sharedManager.headers).validate()
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
    
    fileprivate func request(courierList endpoint: courierListEndpoint) -> DataRequest {
        return Alamofire.request(baseUrl + endpoint.path,
                                 method: endpoint.method,
                                 parameters: endpoint.parameters,
                                 encoding: API.sharedManager.encoding,
                                 headers: API.sharedManager.headers).validate()
    }
    
    enum courierListEndpoint {
        case all(Int, Int)
        
        var path: String {
            switch self {
            case .all(let pageIndex, let pageSize):
                return "courier/" + "\(pageIndex)" + "\(pageSize)"
            }
        }
        
        var method: HTTPMethod {
            switch self {
            case .all:
                return .get
            }
        }
        
        var parameters: [String: Any]? {
            switch self {
            case .all:
                return nil
            }
        }
    }
    
}

extension API {
    
    fileprivate func request(courierAdd endpoint: courierAddEndpoint) -> DataRequest {
        return Alamofire.request(baseUrl + endpoint.path,
                                 method: endpoint.method,
                                 parameters: endpoint.parameters,
                                 encoding: API.sharedManager.encoding,
                                 headers: API.sharedManager.headers).validate()
    }
    
    enum courierAddEndpoint {
        case new(CourierAddRequest)
        
        var path: String {
            switch self {
            case .new:
                return "courier/"
            }
        }
        
        var method: HTTPMethod {
            switch self {
            case .new:
                return .post
            }
        }
        
        var parameters: [String: Any]? {
            switch self {
            case .new(let courierAddRequest):
                return ["ownerId": courierAddRequest.ownerId,
                        "origin" : courierAddRequest.origin,
                        "originDate": courierAddRequest.originDate,
                        "destination": courierAddRequest.destination,
                        "destinationDate": courierAddRequest.destinationDate,
                        "weight": courierAddRequest.weight,
                        "instantBooking": courierAddRequest.instantBooking,
                        "price": courierAddRequest.price,
                        "note": courierAddRequest.note]
            }
        }
    }
    
}

extension API {
    
    fileprivate func request(orders endpoint: ordersEndpoint) -> DataRequest {
        return Alamofire.request(baseUrl + endpoint.path,
                                 method: endpoint.method,
                                 parameters: endpoint.parameters,
                                 encoding: API.sharedManager.encoding,
                                 headers: API.sharedManager.headers).validate()
    }
    
    enum ordersEndpoint {
        case current
        case past

        var path: String {
            switch self {
            case .current:
                return "/user/" + API.sharedManager.userID + "/courier/book/current/0/20"
            case .past:
                return "/user/" + API.sharedManager.userID + "/courier/book/past/0/20"
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
