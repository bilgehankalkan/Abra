
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
        request(courier: .list(pageIndex, pageSize)).responseArray(keyPath: keyPath) {
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
    
//    func courier(add request: CourierAddRequest, completion: @escaping (CourierAddResponse?, Error?) -> Void) {
//        request(courier: .add(request)).responseObject(keyPath: keyPath) {
//            (response: DataResponse<CourierAddResponse>) in
//            if response.error != nil {
//                completion(nil, response.error)
//            }
//            if let courierAddResponse = response.result.value {
//                completion(courierAddResponse, nil)
//            }
//            else {
//                completion(nil, response.result.error)
//            }
//        }
//    }
    
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
    
    fileprivate func request(courier endpoint: courierEndpoint) -> DataRequest {
        return Alamofire.request(baseUrl + endpoint.path,
                                 method: endpoint.method,
                                 parameters: endpoint.parameters,
                                 encoding: API.sharedManager.encoding,
                                 headers: API.sharedManager.headers).validate()
    }
    
    enum courierEndpoint {
        case list(Int, Int)
        case add(CourierAddRequest)
        
        var path: String {
            switch self {
            case .list(let pageIndex, let pageSize):
                return "courier/" + "\(pageIndex)" + "\(pageSize)"
            case .add:
                return "courier/"
            }
        }
        
        var method: HTTPMethod {
            switch self {
            case .list:
                return .get
            case .add:
                return .post
            }
        }
        
        var parameters: [String: Any]? {
            switch self {
            case .list:
                return nil
            case .add(let courierAddRequest):
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
