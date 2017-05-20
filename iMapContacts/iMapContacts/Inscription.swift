//
//  Inscription.swift
//  iMapContacts
//
//  Created by m2sar on 27/04/17.
//  Copyright © 2017 m2sar. All rights reserved.
//

import Foundation
import UIKit

class Inscription: UIViewController {
    @IBOutlet weak var lastname: UITextField!
    @IBOutlet weak var firstName: UITextField!
    @IBOutlet weak var email: UITextField!
    @IBOutlet weak var password: UITextField!
    @IBOutlet weak var passwordRepeat: UITextField!
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
<<<<<<< HEAD
    private func checkPassword() -> Bool{
        return ( (password.text! == passwordRepeat.text) && !(password.text!.isEmpty) && !(passwordRepeat.text!.isEmpty) )
    }
    
    private func sendInscription() {
        //on suppose que sa marche !
        var url = ""
        let data : Data?
        var request = URLRequest(url: URL(string: url)!)
        request.httpMethod = "POST"
        request.setValue("application/x-www-form-urlencoded", forHTTPHeaderField: "Content-Type")
        data = "fname=\(firstName.text)&lname=\(lastname.text)&email=\(email.text)&password=\(password.text)".data(using: String.Encoding.ascii, allowLossyConversion: false)
        request.httpBody = data
        
=======
    
    private func checkPassword() -> Bool{
        return ( !(password.text! == passwordRepeat.text) && !(password.text!.isEmpty) && !(passwordRepeat.text!.isEmpty) )
    }
    
    private func sendInscription() -> Bool{
        var urlComponents = URLComponents()
        guard let mail = email.text, let pass = password.text, let passR = passwordRepeat.text, let lname = lastname.text , let fname = firstName.text else { return false }
        guard mail.characters.count >= 6, pass.characters.count >= 4 else { return false} // Handle error todo
        
        urlComponents.queryItems = [
            URLQueryItem(name: "lname" , value : lastname.text!.addingPercentEncoding(withAllowedCharacters: CharacterSet.alphanumerics)),
            URLQueryItem(name: "fname" , value : firstName.text!.addingPercentEncoding(withAllowedCharacters: CharacterSet.alphanumerics)),
            URLQueryItem(name: "email" , value : email.text!.addingPercentEncoding(withAllowedCharacters: CharacterSet.alphanumerics)),
            URLQueryItem(name: "password" , value : password.text!.addingPercentEncoding(withAllowedCharacters: CharacterSet.alphanumerics)),
        ]
        let urlUtils = UrlUtils().sendToServ(httpMethod: HTTPMETHOD.POST, collection: USER.ROOT.rawValue, urlComponents: urlComponents)
        
        let ableToConnect : Bool = true
        //TODO : Faire un check des données reçu puis set le bool en fonction de si la connexion est possible ou non
        
        if (ableToConnect) {
            return true
        }else{
            return false
        }
>>>>>>> florent
    }
    
    private func isNotEmpty() -> Bool {
        if ((lastname.text?.isEmpty)! || (firstName.text?.isEmpty)! || (email.text?.isEmpty)! || (password.text?.isEmpty)! || (passwordRepeat.text?.isEmpty)! ) {
<<<<<<< HEAD
            let alertView = UIAlertController()
            self.present(alertView, animated: true, completion: nil)
            alertView.addAction(UIAlertAction(title:"Veuillez remplir tous les champs", style: .cancel, handler: {(action: UIAlertAction!) in
            }))
=======
            AlertView().showAlertView(targetVC: self, title: "Veuillez remplir tous les champs", message: "")
>>>>>>> florent
            return false
        }
        else {
            return true
        }
<<<<<<< HEAD
        
=======
>>>>>>> florent
    }
    
    @IBAction func valider_Action(_ sender: UIButton) {
        if (isNotEmpty()) {
            print("test passe")
            if (checkPassword()) {
<<<<<<< HEAD
                
                sendInscription()
            }
            else {
                let alertView = UIAlertController()
                self.present(alertView, animated: true, completion: nil)
                alertView.addAction(UIAlertAction(title:"Les mots de passe doivent être identiques", style: .cancel, handler: {(action: UIAlertAction!) in
                }))
=======
                sendInscription()
            }
            else {
              
>>>>>>> florent
            }
        }
    }
    
    
    //        let jsonObject: NSMutableDictionary = NSMutableDictionary()
    //        let jsonData : NSData
    //        let jsonString : NSString
    //
    //        jsonObject.setValue(lastname.text, forKey: "lastName")
    //        jsonObject.setValue(firstName.text, forKey: "firstName")
    //        jsonObject.setValue(email.text, forKey: "email")
    //        jsonObject.setValue(password.text, forKey: "password")
    //
    //        do {
    //            jsonData = try JSONSerialization.data(withJSONObject: jsonObject, options: JSONSerialization.WritingOptions()) as NSData
    //            jsonString = NSString(data: jsonData as Data , encoding: String.Encoding.utf8.rawValue) as! String as NSString
    //            print("jsonData = \(jsonData)")
    //            print("json string = \(jsonString)")
    //        }catch _ {
    //            print("FAIL")
    //        }
    
}
