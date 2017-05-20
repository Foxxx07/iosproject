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
<<<<<<< HEAD
=======
        
>>>>>>> florent
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
<<<<<<< HEAD
    private func sendConnexion() {
        //on suppose que sa marche aussi !
        var request = URLRequest(url: URL(string:"http://www.google.fr/u/")!)
        request.httpMethod = "POST"
        
        var urlComponents = URLComponents()
        urlComponents.queryItems = [
            URLQueryItem(name: "email" , value : email.text),
            URLQueryItem(name: "password", value : password.text)
        ]
        
        guard let mail = email.text, let pass = password.text else { return } //todo
        guard mail.characters.count >= 6, pass.characters.count >= 4 else { return } // Handle error todo
        guard let parameters = urlComponents.query?.addingPercentEncoding(withAllowedCharacters: CharacterSet.alphanumerics) else { return }
        request.httpBody = parameters.data(using: .ascii)
        request.setValue("application/x-www-form-urlencoded", forHTTPHeaderField: "Content-Type")
        let task = URLSession.shared.dataTask(with: request, completionHandler: {(data,response,error) in })
        print( request.httpBody)
        print(urlComponents.url)
        task.resume()
        
            
//        let session = URLSession.shared.dataTask(with: request, completionHandler: {(data,response,error) in

            
        
        
    }
    
    @IBAction func seConnecter(_ sender: UIButton) {
        let a: Bool = false
        if (a == false) {
            self.performSegue(withIdentifier: "acceuil", sender: self)
            sendConnexion()
            
        }
        else {
            let alertView = UIAlertController()
            self.present(alertView, animated: true, completion: nil)
            alertView.addAction(UIAlertAction(title:"Login ou mot de passe incorrect", style: .cancel, handler: {(action: UIAlertAction!) in
            }))
=======
    private func sendConnexion() -> Bool{
     
        var urlComponents = URLComponents()
        
        guard let mail = email.text, let pass = password.text else {return false}
        guard mail.characters.count >= 6, pass.characters.count >= 4 else { return false } // Handle error todo
        
        urlComponents.queryItems = [
            URLQueryItem(name: "email" , value : email.text!.addingPercentEncoding(withAllowedCharacters: CharacterSet.alphanumerics)),
            URLQueryItem(name: "password", value : password.text!.addingPercentEncoding(withAllowedCharacters: CharacterSet.alphanumerics))
            ]
        
        let dataTask =  UrlUtils().sendToServ(httpMethod: HTTPMETHOD.POST, collection: USER.ME.rawValue, urlComponents: urlComponents)

        let ableToConnect : Bool = true
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
>>>>>>> florent
        }
    }
    
    @IBAction func signUp(_ sender: UIButton) {
<<<<<<< HEAD
        print("Hello World")
        self.performSegue(withIdentifier: "inscrire", sender: self)
    }
    
    
=======
        self.performSegue(withIdentifier: "inscrire", sender: self)
    }
    
>>>>>>> florent
}

