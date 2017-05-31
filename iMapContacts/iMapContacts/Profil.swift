//
//  Profil.swift
//  iMapContacts
//
//  Created by m2sar on 27/05/17.
//  Copyright © 2017 m2sar. All rights reserved.
//

import UIKit

class Profil: UIViewController, UIImagePickerControllerDelegate, UINavigationControllerDelegate{
    
    @IBOutlet weak var profilePicture: UIImageView!
    @IBOutlet weak var displayPosition: UISwitch!
    @IBOutlet weak var email: UITextField!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        getEmail()
        let tap: UITapGestureRecognizer = UITapGestureRecognizer(target: self, action: "dismissKeyboard")
        view.addGestureRecognizer(tap)
       // if (UserDefaults.standard.value(forKey: "profilePicture") != nil){
        //    profilePicture = UserDefaults.standard.value(forKey: "profilePicture") as! UIImageView
       // }
        
    }
    
    
    @IBAction func onOffPosition(_ sender: AnyObject) {
        if (displayPosition.isOn) {
            //
        }
        else {
            //
        }
    }
    
    @IBAction func changeMail(_ sender: AnyObject) {
        var urlComponents = URLComponents()
        
        urlComponents.queryItems = [
            URLQueryItem(name: "email" , value : email.text!.addingPercentEncoding(withAllowedCharacters: CharacterSet.alphanumerics))
            ]
        guard let mail = email.text else { return }
        let urlUtil = UrlUtils()
        urlUtil.sendToServ(httpMethod: HTTPMETHOD.POST, collection: USER.ME.rawValue, urlComponents: urlComponents, callback: { (data, response, error) in
            if let statusCode = response as? HTTPURLResponse {
                if (statusCode.statusCode == 200) {
                    do {
                        if let data = data {
                            let json =  try JSONSerialization.jsonObject(with: data, options: JSONSerialization.ReadingOptions.mutableContainers) as? NSDictionary
                            
                            if let value : Int = json?.value(forKey: "c") as! Int? {
                                if (value == 0) {
                                    if let d : String = json?.value(forKey: "data") as! String {
                                        
                                    }
                                }
                            }
                        }
                    }
                    catch let error{
                        print(error)// Todo ?
                    }
                }
            else if (statusCode.statusCode == 404) {
                
                }
             else {
                // ...
                }
            }
        })
    }
    
    func getEmail(){
        let urlComponents = URLComponents()
        let urlUtil = UrlUtils()
        urlUtil.sendToServ(httpMethod: HTTPMETHOD.GET, collection: USER.ME.rawValue, urlComponents: urlComponents, callback: { (data, response, error) in
            if let statusCode = response as? HTTPURLResponse {
                if (statusCode.statusCode == 200) {
                    do {
                    if let data = data {
                        let json = try JSONSerialization.jsonObject(with: data, options: JSONSerialization.ReadingOptions.mutableContainers) as? NSDictionary
                        if let value: Int = json?.value(forKey: "c") as! Int?{
                            if (value == 0){
                                 let metadata = json?["data"] as? [[String: AnyObject]]
                                    for data in metadata! {
                                        if let mail = data["email"] as? String {
                                            self.email.text = mail
                                        }
                                
                                    }
                                }
                            }
                        
                    }
                    } catch let error {
                        print(error)
                    }
                }
            }
        })
    }
    
    
    @IBAction func changePassword(_ sender: AnyObject) {
    }
    
    
    @IBAction func selectPicture(_ sender: UIButton) {
        let imagePickerController = UIImagePickerController()
        imagePickerController.delegate = self
        
        let actionSheet = UIAlertController(title:nil, message:nil, preferredStyle:
            .actionSheet)
        self.present(actionSheet, animated: true, completion: nil)
        
        
        actionSheet.addAction(UIAlertAction(title:"Choisir une photo", style: .default, handler: { (action: UIAlertAction) in
            imagePickerController.sourceType = .photoLibrary
            self.present(imagePickerController, animated: true, completion: nil)
            
        }))
        
        actionSheet.addAction(UIAlertAction(title: "Cancel", style: .cancel, handler: nil))
        
    }
    
    func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [String : Any]) {
        let image = info[UIImagePickerControllerOriginalImage] as! UIImage
        //UserDefaults.standard.setValue(image, forKey: "profilePicture")
        profilePicture.image = image
        picker.dismiss(animated: true, completion: nil)
    }
    
    func imagePickerControllerDidCancel(_ picker: UIImagePickerController) {
        picker.dismiss(animated: true, completion: nil)
    }
    
    func dismissKeyboard() {
        view.endEditing(true)
    }
    
    
    
    
}
