//
//  OSBaseViewController.swift
//  Abra
//
//  Created by Hakan Eren on 17/02/2018.
//  Copyright © 2018 Hakan Eren. All rights reserved.
//

import UIKit

struct OfferSelect {
    var originLocation: Location?
    var originDate: Date?
    var destinationLocation: Location?
    var destinationDate: Date?
    var weight: Int?
    var price: Int?
    var instantBooking: Bool?
    var note: String?
}

class OSBaseViewController: UIViewController {
    
    static var offerSelect = OfferSelect()
    
    lazy fileprivate var offerStoryboard        = UIStoryboard(name: "Offer", bundle: .main)
    lazy fileprivate var locationViewController = offerStoryboard.instantiateViewController(withIdentifier: "OSLocationViewController") as! OSLocationViewController
    lazy fileprivate var dateViewController     = offerStoryboard.instantiateViewController(withIdentifier: "OSDateViewController")     as! OSDateViewController
    lazy fileprivate var timeViewController     = offerStoryboard.instantiateViewController(withIdentifier: "OSTimeViewController")     as! OSTimeViewController
    lazy fileprivate var amountViewController   = offerStoryboard.instantiateViewController(withIdentifier: "OSAmountViewController")   as! OSAmountViewController
    lazy fileprivate var switchViewController   = offerStoryboard.instantiateViewController(withIdentifier: "OSSwitchViewController")   as! OSSwitchViewController
    lazy fileprivate var noteViewController     = offerStoryboard.instantiateViewController(withIdentifier: "OSNoteViewController")     as! OSNoteViewController
    lazy fileprivate var finishViewController   = offerStoryboard.instantiateViewController(withIdentifier: "OSFinishViewController")   as! OSFinishViewController

    func startOS() {
        locationViewController.mode = .origin
        let baseNavigationControllers = BaseNavigationController(rootViewController: locationViewController)
        present(baseNavigationControllers, animated: true)
    }
    
    func showLocation(for mode: OSLocationMode, from viewController: OSBaseViewController) {
        locationViewController.mode = mode
        viewController.navigationController?.pushViewController(locationViewController, animated: true)
    }
    
    func showDate(for mode: OSDateMode, from viewController: OSBaseViewController) {
        dateViewController.mode = mode
        viewController.navigationController?.pushViewController(dateViewController, animated: true)
    }

    func showTime(for mode: OSTimeMode, from viewController: OSBaseViewController) {
        timeViewController.mode = mode
        viewController.navigationController?.pushViewController(timeViewController, animated: true)
    }

    func showAmount(for mode: OSAmountMode, from viewController: OSBaseViewController) {
        amountViewController.mode = mode
        viewController.navigationController?.pushViewController(amountViewController, animated: true)
    }

    func showSwitch(for mode: OSSwitchMode, from viewController: OSBaseViewController) {
        switchViewController.mode = mode
        viewController.navigationController?.pushViewController(switchViewController, animated: true)
    }

    func showNote(for mode: OSNoteMode, from viewController: OSBaseViewController) {
        noteViewController.mode = mode
        viewController.navigationController?.pushViewController(noteViewController, animated: true)
    }
    
    func showFinish(from viewController: OSBaseViewController) {
        viewController.navigationController?.pushViewController(finishViewController, animated: true)
    }
    
    func finishOS() {
        print("-")
        print(OSBaseViewController.offerSelect)
        print("-")
    }

    @IBAction func back(_ sender: UIButton) {
        navigationController?.popViewController(animated: true)
    }
    
    @IBAction func close(_ sender: UIButton) {
        dismiss(animated: true)
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        navigationController?.setNavigationBarHidden(true, animated: false)
    }
    
}
