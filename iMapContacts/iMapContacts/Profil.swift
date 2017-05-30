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
    
    override func viewDidLoad() {
        super.viewDidLoad()
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
    
    
    
}
