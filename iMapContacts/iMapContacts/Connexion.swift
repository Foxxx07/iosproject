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
        }
    }
    
    @IBAction func signUp(_ sender: UIButton) {
        print("Hello World")
        self.performSegue(withIdentifier: "inscrire", sender: self)
    }
    
    
}

