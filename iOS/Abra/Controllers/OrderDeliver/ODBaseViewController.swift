//
//  OFBaseViewController.swift
//  Abra
//
//  Created by Hakan Eren on 17/02/2018.
//  Copyright © 2018 Hakan Eren. All rights reserved.
//

import UIKit

class ODBaseViewController: UIViewController {
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setupBarButtons()
    }
    
    func setupBarButtons() {
        if let mainNavigationController = self.navigationController as? BaseNavigationController {
            navigationItem.setLeftBarButton(mainNavigationController.profileBarButtonItem, animated: true)
            navigationItem.setRightBarButton(mainNavigationController.inboxBarButtonItem, animated: true)
        }
    }

}
