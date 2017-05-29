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
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    
    private func checkPassword() -> Bool{
        return ( (password.text! == passwordRepeat.text) && !(password.text!.isEmpty) && !(passwordRepeat.text!.isEmpty) )
    }
    
    private func sendInscription() -> Bool{
        var urlComponents = URLComponents()
        guard let mail = email.text, let pass = password.text, let passR = passwordRepeat.text, let lname = lastname.text , let fname = firstName.text else { return false }
        
        urlComponents.queryItems = [
            URLQueryItem(name: "lname" , value : lastname.text!.addingPercentEncoding(withAllowedCharacters: CharacterSet.alphanumerics)),
            URLQueryItem(name: "fname" , value : firstName.text!.addingPercentEncoding(withAllowedCharacters: CharacterSet.alphanumerics)),
            URLQueryItem(name: "email" , value : email.text!.addingPercentEncoding(withAllowedCharacters: CharacterSet.alphanumerics)),
            URLQueryItem(name: "password" , value : password.text!.addingPercentEncoding(withAllowedCharacters: CharacterSet.alphanumerics)),
        ]
        
        let urlUtil = UrlUtils()
        urlUtil.sendToServ(httpMethod: HTTPMETHOD.POST, collection: USER.ROOT.rawValue, urlComponents: urlComponents, callback: { (data, response, error) in
            if let data = data {
            let json =  ( try? JSONSerialization.jsonObject(with: data, options: .mutableContainers)) as? NSDictionary
                print(json)
            }
            
            do {
                if let data = data {
                    let json =  try JSONSerialization.jsonObject(with: data, options: JSONSerialization.ReadingOptions.mutableContainers) as Any
                    print(json)
                    let jsonRead = json as! NSDictionary
                    print(jsonRead.value(forKey: "c"))
                    
                    
                }
                
            }
            catch let error{
                print(error)
            
            }
            print(data)
        })
        
        let ableToConnect : Bool = true
        //TODO : Faire un check des données reçu puis set le bool en fonction de si la connexion est possible ou non
        
        if (ableToConnect) {
            return true
        }else{
            return false
        }
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
            print("test passe")
            if (checkPassword()) {
                sendInscription()
            }
            else {
              
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

