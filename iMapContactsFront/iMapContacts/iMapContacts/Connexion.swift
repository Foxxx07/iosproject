//
//  ViewController.swift
//  iMapContacts
//
//  Created by m2sar on 27/04/17.
//  Copyright © 2017 m2sar. All rights reserved.
//

import UIKit

class Connexion: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    @IBAction func seConnecter(_ sender: UIButton) {
        let a: Bool = false
        if (a == false) {
            self.performSegue(withIdentifier: "acceuil", sender: self)
            
            
        }
        else {
            let alertView = UIAlertController()
            self.present(alertView, animated: true, completion: nil)
            alertView.addAction(UIAlertAction(title:"Login ou mot de passe incorrect", style: .cancel, handler: {(action: UIAlertAction!) in
            }))
        }
    }
    
    @IBAction func sInscrire(_ sender: UIButton) {
        self.performSegue(withIdentifier: "inscription", sender: self)
    }

    
    
}

