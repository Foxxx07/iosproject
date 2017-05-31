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
    var alertView = AlertView.init(title: "Error", message: "", preferredStyle: UIAlertControllerStyle.alert)
    
    


    
    override func viewDidLoad() {
        super.viewDidLoad()
        let tap: UITapGestureRecognizer = UITapGestureRecognizer(target: self, action: "dismissKeyboard")
        view.addGestureRecognizer(tap)
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    
    private func checkPassword() -> Bool{
        return ( (password.text! == passwordRepeat.text) && !(password.text!.isEmpty) && !(passwordRepeat.text!.isEmpty) )
    }
    
    private func sendInscription(){
        var urlComponents = URLComponents()
        guard let mail = email.text, let pass = password.text, let passR = passwordRepeat.text, let lname = lastname.text , let fname = firstName.text else { return  }
        
        urlComponents.queryItems = [
            URLQueryItem(name: "lname" , value : lastname.text!.addingPercentEncoding(withAllowedCharacters: CharacterSet.alphanumerics)),
            URLQueryItem(name: "fname" , value : firstName.text!.addingPercentEncoding(withAllowedCharacters: CharacterSet.alphanumerics)),
            URLQueryItem(name: "email" , value : email.text!.addingPercentEncoding(withAllowedCharacters: CharacterSet.alphanumerics)),
            URLQueryItem(name: "password" , value : password.text!.addingPercentEncoding(withAllowedCharacters: CharacterSet.alphanumerics)),
        ]
        
        let urlUtil = UrlUtils()
        urlUtil.sendToServ(httpMethod: HTTPMETHOD.POST, collection: USER.ROOT.rawValue, urlComponents: urlComponents, callback: { (data, response, error) in
            if let statusCode = response as? HTTPURLResponse {
                if (statusCode.statusCode == 200) {
                    do {
                        if let data = data {
                            let json =  try JSONSerialization.jsonObject(with: data, options: JSONSerialization.ReadingOptions.mutableContainers) as? NSDictionary
                            print(json)
                            
                            if let value : Int = json?.value(forKey: "c") as! Int? {
                                if (value == 0) {
                                    if let token = json?.value(forKey: "data") as! String? {
                                        if (token.characters.count == 8){
                                            UserDefaults.standard.set(token, forKey: "token")
                                            UserDefaults.standard.synchronize()
                                        }
                                    }
                                    
                                } else if (value == 2){
                                    self.alertView.setMessage(message: "E-mail déjà utilisé")
                                    self.alertView.showAlertView(targetVC: self)
                                    
                                }
                            }
                        }
                    }
                    catch let error{
                        print(error)
                        
                    }
                } else if (statusCode.statusCode == 404) {
                    self.alertView.setMessage(message: "L'insciption a échoué")
                }
            } else {
                // ...
            }
        })
        
      
    }
    
    private func isNotEmpty() -> Bool {
        if ((lastname.text?.isEmpty)! || (firstName.text?.isEmpty)! || (email.text?.isEmpty)! || (password.text?.isEmpty)! || (passwordRepeat.text?.isEmpty)! ) {
            self.alertView.setTitle(title: "Veuillez remplir tous les champs")
            self.alertView.showAlertView(targetVC: self)
            return false
        }
        else {
            return true
        }
    }
    
    @IBAction func valider_Action(_ sender: UIButton) {
        if (isNotEmpty()) {
            if (checkPassword()) {
                sendInscription()
            }
            else {
                self.alertView.setMessage(message: "Password invalide")
                self.alertView.showAlertView(targetVC: self)
            }
           
        }
        else {
            self.alertView.setMessage(message: "Remplissez les champs vides")
            self.alertView.showAlertView(targetVC: self)
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
    
    func dismissKeyboard() {

        view.endEditing(true)
    }
    
}

