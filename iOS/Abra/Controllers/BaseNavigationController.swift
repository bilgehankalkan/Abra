
import UIKit

class BaseNavigationController: UINavigationController {
    
    var profileBarButtonItem: UIBarButtonItem {
        return UIBarButtonItem(image: UIImage(named: "nav-profile"),
                               style: .plain,
                               target: self,
                               action: #selector(showProfile(_:)))
    }
    
    var inboxBarButtonItem: UIBarButtonItem {
        return UIBarButtonItem(image: UIImage(named: "nav-inbox"),
                               style: .plain,
                               target: self,
                               action: #selector(showInbox(_:)))
    }
    
    @objc private func showProfile(_ sender: UIBarButtonItem) {
        presentViewController(withIdentifier: "ProfileViewController", animated: true)
    }
    
    @objc private func showInbox(_ sender: UIBarButtonItem) {
        presentViewController(withIdentifier: "InboxViewController", animated: true)
    }
    
    private func presentViewController(withIdentifier identifier: String, animated: Bool) {
        if let viewController = storyboard?.instantiateViewController(withIdentifier: identifier) {
            present(viewController, animated: animated)
        }
    }
    private func pushViewController(withIdentifier identifier: String, animated: Bool) {
        if let viewController = storyboard?.instantiateViewController(withIdentifier: identifier) {
            pushViewController(viewController, animated: animated)
        }
    }
    
    override var prefersStatusBarHidden: Bool {
        return false
    }
    
    override var preferredStatusBarStyle: UIStatusBarStyle {
        return .default
    }
    
}

extension BaseNavigationController: UIGestureRecognizerDelegate {
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        interactivePopGestureRecognizer?.delegate = self
    }
    
    func gestureRecognizer(_ gestureRecognizer: UIGestureRecognizer, shouldRecognizeSimultaneouslyWith otherGestureRecognizer: UIGestureRecognizer) -> Bool {
        return true
    }
    
    func gestureRecognizer(_ gestureRecognizer: UIGestureRecognizer, shouldBeRequiredToFailBy otherGestureRecognizer: UIGestureRecognizer) -> Bool {
        return gestureRecognizer is UIScreenEdgePanGestureRecognizer
    }

}
