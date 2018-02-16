//
//  FindViewController.swift
//  Abra
//
//  Created by Hakan Eren on 17/02/2018.
//  Copyright © 2018 Hakan Eren. All rights reserved.
//

import UIKit

class FindViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        if let mainNavigationController = self.navigationController as? BaseNavigationController {
            navigationItem.setLeftBarButton(mainNavigationController.profileBarButtonItem, animated: true)
            navigationItem.setRightBarButton(mainNavigationController.inboxBarButtonItem, animated: true)
        }
        
        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
