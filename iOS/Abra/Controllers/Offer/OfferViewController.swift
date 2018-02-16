//
//  OfferViewController.swift
//  Abra
//
//  Created by Hakan Eren on 17/02/2018.
//  Copyright © 2018 Hakan Eren. All rights reserved.
//

import UIKit

class OfferViewController: UIViewController {
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        beginOS()
    }
    
    private func beginOS() {
        let offerStoryboard = UIStoryboard(name: "Offer", bundle: .main)
        let viewController = offerStoryboard.instantiateViewController(withIdentifier: "OSOriginLocationViewController")
        present(viewController, animated: true) {
            self.tabBarController?.selectedIndex = 1
        }
    }

}
