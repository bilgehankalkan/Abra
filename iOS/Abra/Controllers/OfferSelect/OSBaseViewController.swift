//
//  OSBaseViewController.swift
//  Abra
//
//  Created by Hakan Eren on 17/02/2018.
//  Copyright © 2018 Hakan Eren. All rights reserved.
//

import UIKit

enum OfferSelectMode {
    case courier
    case carry
}

struct OfferSelect {
    var originLocation: Location?
    var originDate: Date?
    var destinationLocation: Location?
    var destinationDate: Date?
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
    
    func finishOS() {
        print("-")
        print(OSBaseViewController.offerSelect)
        print("-")
        
        OSBaseViewController.offerSelect = OfferSelect()
        switch OSBaseViewController.offerSelectMode {
        case .courier:
            dismiss(animated: false) {
                
            }
        case .carry:
            dismiss(animated: true)
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
