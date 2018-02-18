
import UIKit
import MBProgressHUD

enum OfferSelectMode {
    case courier
    case carry
}

struct OfferSelect {
    var origin: Location?
    var destination: Location?
    var weight: Int?
    var instantBooking: Bool?
    var price: Int?
    var note: String?
}

class OSBaseViewController: UIViewController {
    
    static var offerSelectMode: OfferSelectMode = .courier
    static var offerSelect = OfferSelect()
    
    lazy fileprivate var mainStoryboard         = UIStoryboard(name: "Main", bundle: .main)
    lazy fileprivate var offerSelectStoryboard  = UIStoryboard(name: "OfferSelect", bundle: .main)
    lazy fileprivate var locationViewController = offerSelectStoryboard.instantiateViewController(withIdentifier: "OSLocationViewController") as! OSLocationViewController
    lazy fileprivate var dateViewController     = offerSelectStoryboard.instantiateViewController(withIdentifier: "OSDateViewController")     as! OSDateViewController
    lazy fileprivate var timeViewController     = offerSelectStoryboard.instantiateViewController(withIdentifier: "OSTimeViewController")     as! OSTimeViewController
    lazy fileprivate var amountViewController   = offerSelectStoryboard.instantiateViewController(withIdentifier: "OSAmountViewController")   as! OSAmountViewController
    lazy fileprivate var switchViewController   = offerSelectStoryboard.instantiateViewController(withIdentifier: "OSSwitchViewController")   as! OSSwitchViewController
    lazy fileprivate var noteViewController     = offerSelectStoryboard.instantiateViewController(withIdentifier: "OSNoteViewController")     as! OSNoteViewController
    lazy fileprivate var finishViewController   = offerSelectStoryboard.instantiateViewController(withIdentifier: "OSFinishViewController")   as! OSFinishViewController
    
    func startOS(mode: OfferSelectMode, completion: (() -> Swift.Void)? = nil) {
        OSBaseViewController.offerSelectMode = mode
        locationViewController.locationMode = .origin
        let baseNavigationControllers = BaseNavigationController(rootViewController: locationViewController)
        if let tabBarController = tabBarController {
            tabBarController.present(baseNavigationControllers, animated: true, completion: completion)
        }
        else {
            present(baseNavigationControllers, animated: true, completion: completion)
        }
    }
    
    func showLocation(for locationMode: OSLocationMode, from viewController: OSBaseViewController) {
        locationViewController.locationMode = locationMode
        viewController.navigationController?.pushViewController(locationViewController, animated: true)
    }
    
    func showDate(for dateMode: OSDateMode, from viewController: OSBaseViewController) {
        dateViewController.dateMode = dateMode
        viewController.navigationController?.pushViewController(dateViewController, animated: true)
    }
    
    func showTime(for timeMode: OSTimeMode, from viewController: OSBaseViewController) {
        timeViewController.timeMode = timeMode
        viewController.navigationController?.pushViewController(timeViewController, animated: true)
    }
    
    func showAmount(for amountMode: OSAmountMode, from viewController: OSBaseViewController) {
        amountViewController.amountMode = amountMode
        viewController.navigationController?.pushViewController(amountViewController, animated: true)
    }
    
    func showSwitch(for switchMode: OSSwitchMode, from viewController: OSBaseViewController) {
        switchViewController.switchMode = switchMode
        viewController.navigationController?.pushViewController(switchViewController, animated: true)
    }
    
    func showNote(for noteMode: OSNoteMode, from viewController: OSBaseViewController) {
        noteViewController.noteMode = noteMode
        viewController.navigationController?.pushViewController(noteViewController, animated: true)
    }
    
    func showFinish(from viewController: OSBaseViewController) {
        viewController.navigationController?.pushViewController(finishViewController, animated: true)
    }
    
    func finishOS(_ viewController: OSBaseViewController) {
        switch OSBaseViewController.offerSelectMode {
        case .courier:
            MBProgressHUD.showAdded(to: viewController.view, animated: true)
            API.shared.courierList(by: OSBaseViewController.offerSelect, completion: {
                (orders: [Order]?, error: Error?) in
                DispatchQueue.global(qos: .userInitiated).async { [unowned self] in
                    DispatchQueue.main.async {
                        MBProgressHUD.hide(for: viewController.view, animated: true)
                        if error != nil {
                            self.presentErrorAlertView(error)
                        }
                        else {
                            OSBaseViewController.offerSelect = OfferSelect()
                            // TODO: DISPLAY LIST OF COURIERS
                            self.dismiss(animated: true) {
                            }
                        }
                    }
                }
            })
        case .carry:
            MBProgressHUD.showAdded(to: viewController.view, animated: true)
            API.shared.courierAdd(by: OSBaseViewController.offerSelect) {
                (courierAddResponse: CourierAddResponse?, error: Error?) in
                DispatchQueue.global(qos: .userInitiated).async { [unowned self] in
                    DispatchQueue.main.async {
                        MBProgressHUD.hide(for: viewController.view, animated: true)
                        if error != nil {
                            self.presentErrorAlertView(error)
                        }
                        else {
                            OSBaseViewController.offerSelect = OfferSelect()
                            // TODO: DISPLAY DETAIL OF THE POST
                            let alertView = UIAlertController(title: NSLocalizedString("Yaayy!", comment: "Used as an exclamation of pleasure, approval, elation, or victory."), message: NSLocalizedString("Your post submitted!\nYou will get a notification as soon as our staff approves your post.", comment: ""), preferredStyle: .alert)
                            alertView.addAction(UIAlertAction(title: NSLocalizedString("Oki", comment: "Dismiss an alert"), style: .cancel, handler: { (alertAction: UIAlertAction) in
                                self.dismiss(animated: true) {
                                }
                            }))
                            self.present(alertView, animated: true, completion: nil)
                        }
                    }
                }
            }
        }
    }
    
    @IBAction func close(_ sender: UIButton) {
        OSBaseViewController.offerSelect = OfferSelect()
        dismiss(animated: true)
    }
    
    @IBAction func back(_ sender: UIButton) {
        navigationController?.popViewController(animated: true)
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        navigationController?.setNavigationBarHidden(true, animated: false)
    }
    
}
