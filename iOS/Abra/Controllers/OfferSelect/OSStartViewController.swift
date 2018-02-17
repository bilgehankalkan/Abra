//
//  OfferViewController.swift
//  Abra
//
//  Created by Hakan Eren on 17/02/2018.
//  Copyright © 2018 Hakan Eren. All rights reserved.
//

import UIKit

class OSStartViewController: OSBaseViewController {
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        beginOS()
    }
    
    private func beginOS() {
        let offerStoryboard = UIStoryboard(name: "Offer", bundle: .main)
        if let viewController = offerStoryboard.instantiateViewController(withIdentifier: "OSLocationViewController") as? OSLocationViewController {
            viewController.offerSelect = OSBaseViewController.offerSelect
            viewController.mode = .origin
            present(viewController, animated: true) {
                self.tabBarController?.selectedIndex = 1
            }
        }
    }

}
