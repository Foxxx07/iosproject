//
//  ViewController.swift
//  iMapContacts
//
//  Created by m2sar on 27/04/17.
//  Copyright © 2017 m2sar. All rights reserved.
//

import UIKit

class Connexion: UIViewController {

    @IBOutlet weak var email: UITextField!
    @IBOutlet weak var password: UITextField!
    
    override func viewDidLoad() {
        
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    private func sendConnexion() -> Bool{
     
        var urlComponents = URLComponents()
        
        guard let mail = email.text, let pass = password.text else {return false}
        guard mail.characters.count >= 6, pass.characters.count >= 4 else { return false } // Handle error todo
        
        urlComponents.queryItems = [
            URLQueryItem(name: "email" , value : email.text!.addingPercentEncoding(withAllowedCharacters: CharacterSet.alphanumerics)),
            URLQueryItem(name: "password", value : password.text!.addingPercentEncoding(withAllowedCharacters: CharacterSet.alphanumerics))
            ]
        
        let dictionary =  UrlUtils().sendToServ(httpMethod: HTTPMETHOD.POST, collection: USER.ME.rawValue, urlComponents: urlComponents)
        let ableToConnect : Bool = false
        //TODO : Faire un check des données reçu puis set le bool en fonction de si la connexion est possible ou non
        
        if (ableToConnect) {
            return true
        }else{
            return false
        }
    }
    
    @IBAction func seConnecter(_ sender: UIButton) {
        let ableToConect: Bool = sendConnexion()
        
        if (ableToConect) {
            self.performSegue(withIdentifier: "acceuil", sender: self)
        }
        else {
            AlertView().showAlertView(targetVC: self, title: "Login ou mot de passe incorrect", message: "")
        }
    }
    
    @IBAction func signUp(_ sender: UIButton) {
        self.performSegue(withIdentifier: "inscrire", sender: self)
    }
    
}

