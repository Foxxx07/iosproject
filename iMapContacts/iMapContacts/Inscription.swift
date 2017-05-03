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
    
    private func checkPassword() -> Bool{
        return ( (password.text! == passwordRepeat.text) && !(password.text!.isEmpty) && !(passwordRepeat.text!.isEmpty) )
    }
    
    private func writeToJSON() {
        let jsonObject: NSMutableDictionary = NSMutableDictionary()
        let jsonData : NSData
        let jsonString : NSString
        
        jsonObject.setValue(lastname.text, forKey: "lastName")
        jsonObject.setValue(firstName.text, forKey: "firstName")
        jsonObject.setValue(email.text, forKey: "email")
        jsonObject.setValue(password.text, forKey: "password")
        
        do {
            jsonData = try JSONSerialization.data(withJSONObject: jsonObject, options: JSONSerialization.WritingOptions()) as NSData
            jsonString = NSString(data: jsonData as Data , encoding: String.Encoding.utf8.rawValue) as! String as NSString
            print("jsonData = \(jsonData)")
            print("json string = \(jsonString)")
        }catch _ {
            print("FAIL")
        }
    }
    
    private func isNotEmpty() -> Bool {
        if ((lastname.text?.isEmpty)! || (firstName.text?.isEmpty)! || (email.text?.isEmpty)! || (password.text?.isEmpty)! || (passwordRepeat.text?.isEmpty)! ) {
            let alertView = UIAlertController()
            self.present(alertView, animated: true, completion: nil)
            alertView.addAction(UIAlertAction(title:"Veuillez remplir tous les champs", style: .cancel, handler: {(action: UIAlertAction!) in
            }))
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
                
                writeToJSON()
            }
            else {
                let alertView = UIAlertController()
                self.present(alertView, animated: true, completion: nil)
                alertView.addAction(UIAlertAction(title:"Les mots de passe doivent être identiques", style: .cancel, handler: {(action: UIAlertAction!) in
                }))
            }
        }
        
        
        
    }
}
