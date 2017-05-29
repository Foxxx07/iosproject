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
    var alertView = AlertView.init(title: "", message: "", preferredStyle: UIAlertControllerStyle.alert)
    var ableToConnect = false
    
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
        
        guard let pass = password.text else {
            alertView.setTitle(title: "password non renseigné")
            return false
        }
        guard let mail = email.text else {
            alertView.setTitle(title: "Email non renseigné")
            return false
        }
      
        guard mail.characters.count >= 1, pass.characters.count >= 4 else {
            var title = ""
            if ( mail.characters.count <= 6 && pass.characters.count <= 4){
                title = "Email et password incorrect"
            }
            else {
                if (mail.characters.count <= 1){
                  title = "Email incorrect"
                }
                else if (pass.characters.count <= 4){
                  title = "Password incorrect"
                }
            }

            alertView.setTitle(title: title)
            return false
        }
        
        
        urlComponents.queryItems = [
            URLQueryItem(name: "email" , value : email.text!.addingPercentEncoding(withAllowedCharacters: CharacterSet.alphanumerics)),
            URLQueryItem(name: "password", value : password.text!.addingPercentEncoding(withAllowedCharacters: CharacterSet.alphanumerics))
            ]
            let urlUtil = UrlUtils()
            //let test =  urlUtil.getMessage(code: 3)
        
            urlUtil.sendToServ(httpMethod: HTTPMETHOD.POST, collection: USER.ME.rawValue, urlComponents: urlComponents, callback: { (data, response, error) in
                if var statusCode = response as? HTTPURLResponse{
                    if (statusCode.statusCode == 200) {
                        do {
                            if let data = data {
                                let json =  try JSONSerialization.jsonObject(with: data, options: JSONSerialization.ReadingOptions.mutableContainers) as? NSDictionary
                                print(json)
                                if let value : Int = json?.value(forKey: "c") as! Int? {
                                    if (value == 1){ // valeur a 0 pour Succes , ici a  1 pour test
                                        self.ableToConnect = true
                                        
                                    }
                                }
                            }
                        }
                        catch let error{
                            print(error)// Todo ?
                            }
                    }
                }
        })
            
        
        let ableToConnect : Bool = true
        //TODO : Faire un check des données reçu puis set le bool en fonction de si la connexion est possible ou non
        
        if (ableToConnect) {
            return true
        } else {
            return false
        }
    }
    
    @IBAction func seConnecter(_ sender: UIButton) {
        let ableToConect: Bool = sendConnexion()
        //let ableToConect: Bool = true
        
        if (ableToConect) {
            self.performSegue(withIdentifier: "acceuil", sender: self)
        }
        else {
            self.alertView.showAlertView(targetVC: self)
        }
    }
    
    @IBAction func signUp(_ sender: UIButton) {
        self.performSegue(withIdentifier: "inscrire", sender: self)
    }
    
}


