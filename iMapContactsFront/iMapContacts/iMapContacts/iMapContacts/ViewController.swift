//
//  ViewController.swift
//  iMapContacts
//
//  Created by m2sar on 29/03/17.
//  Copyright © 2017 m2sar. All rights reserved.
//

import UIKit

class ViewController: UIViewController {
    @IBOutlet weak var identifiant: UITextField!
    @IBOutlet weak var password: UITextField!
    @IBOutlet weak var seConnecter: UIButton!
    @IBOutlet weak var logo: UIImageView!
  
    @IBAction func seConnecter(_ sender: UIButton) {
        //todo
        let a: Bool = false
        if (a == true){
           self.performSegue(withIdentifier:"connexion", sender: self)
        }
        else {
            let alertView = UIAlertController()
            
            self.present(alertView, animated: true, completion: nil)
            alertView.addAction(UIAlertAction(title: "Login ou mot de passe incorrect", style: .cancel, handler: { (action: UIAlertAction!) in
            }))
            
        }
    }
   
    @IBAction func sInscrire(_ sender: UIButton) {
        self.performSegue(withIdentifier:"inscription", sender: self)
    }
    
    let blueColor = (UIColor(red: 24/255, green: 171/255, blue: 219/255, alpha: 1.0))

    override func viewDidLoad() {
        super.viewDidLoad()
        borderSettings()
        
        logo = UIImageView(image: UIImage(named: "iMapContacts/Assets.xcassets/Images/logo.png"))
        
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    private func borderSettings() {
        
//        identifiant.layer.borderWidth = 1.0
//        identifiant.layer.borderColor = blueColor.cgColor
//        
//        password.layer.borderWidth = 1.0
//        password.layer.borderColor = blueColor.cgColor
        
        seConnecter.layer.borderWidth = 1.0
        seConnecter.layer.borderColor = blueColor.cgColor
        
        
    }
    
    


}

