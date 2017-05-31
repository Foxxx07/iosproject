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
   
    
    override func viewDidLoad() {
        super.viewDidLoad()
        let tap: UITapGestureRecognizer = UITapGestureRecognizer(target: self, action: "dismissKeyboard")
        view.addGestureRecognizer(tap)
    }
   
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    private func sendConnexion(){
     
        var urlComponents = URLComponents()
        
        guard let pass = password.text else {
            alertView.setTitle(title: "Password non renseigné")
            alertView.showAlertView(targetVC: self)
            return
        }
        guard let mail = email.text else {
            alertView.setTitle(title: "Email non renseigné")
            alertView.showAlertView(targetVC: self)
            return
        }
      
        guard mail.characters.count >= 6, pass.characters.count >= 4 else {
            var title = ""
            if ( mail.characters.count < 6 && pass.characters.count < 4){
                title = "Email et password incorrect"
            }
            else {
                if (mail.characters.count < 6){
                  title = "Email incorrect"
                }
                else if (pass.characters.count < 4){
                  title = "Password incorrect"
                }
            }

            alertView.setTitle(title: title)
            alertView.showAlertView(targetVC: self)
            return
        }
        
        
        urlComponents.queryItems = [
            URLQueryItem(name: "email" , value : email.text!.addingPercentEncoding(withAllowedCharacters: CharacterSet.alphanumerics)),
            URLQueryItem(name: "password", value : password.text!.addingPercentEncoding(withAllowedCharacters: CharacterSet.alphanumerics))
            ]
            
            let urlUtil = UrlUtils()
            self.performSegue(withIdentifier: "acceuil", sender: self) // HACK
           urlUtil.sendToServ(httpMethod: HTTPMETHOD.POST, collection: USER.ME.rawValue, urlComponents: urlComponents, callback: { (data, response, error) in
                if let statusCode = response as? HTTPURLResponse {
                    if (statusCode.statusCode == 200) {
                        do {
                            if let data = data {
                                let json =  try JSONSerialization.jsonObject(with: data, options: JSONSerialization.ReadingOptions.mutableContainers) as? NSDictionary
                                print(json)
                                print(UserDefaults.standard.value(forKey: "token"))

                                if let value : Int = json?.value(forKey: "c") as! Int? {
                                    if (value == 0) { // valeur 0 pour Succes
                                        if (UserDefaults.standard.value(forKey: "token") != nil){
                                            print(UserDefaults.standard.value(forKey: "token"))
                                            self.performSegue(withIdentifier: "acceuil", sender: self)
                                        }
                                    } else {
                                        if ( value == 3){
                                            self.alertView.setMessage(message: urlUtil.getMessage(code: 3))
                                        }
                                        if (value == 4) {
                                            self.alertView.setMessage(message: urlUtil.getMessage(code: 4))
                                        }
                                        if (value == 5) {
                                            self.alertView.setMessage(message: urlUtil.getMessage(code: 5))
                                        }
                                        self.alertView.showAlertView(targetVC: self)
                                    }
                                }
                            }
                        }
                        catch let error{
                            print(error)// Todo ?
                        }
                    } else if (statusCode.statusCode == 404) {
                        
                    }
                } else {
                    // ...
                }
    })
        

    }
    
    @IBAction func seConnecter(_ sender: UIButton) {
        sendConnexion()
    }
    
    @IBAction func signUp(_  : UIButton) {
        self.performSegue(withIdentifier: "inscrire", sender: self)
    }
    
    func dismissKeyboard() {
        view.endEditing(true)
    }
    
}


