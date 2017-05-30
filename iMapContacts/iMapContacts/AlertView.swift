//
//  AlertView.swift
//  iMapContacts
//
//  Created by m2sar on 09/05/17.
//  Copyright © 2017 m2sar. All rights reserved.
//

import Foundation
import UIKit

class AlertView {
    var alertView : UIAlertController
    
    init(title : String , message : String , preferredStyle : UIAlertControllerStyle) {
        alertView = UIAlertController(title: title, message: message, preferredStyle: UIAlertControllerStyle.alert)
        alertView.addAction(UIAlertAction(title: "Ok", style: .cancel, handler: {(action: UIAlertAction!) in
        }))
      
    }
    
    func showAlertView(targetVC : UIViewController , title : String , message : String){
        self.alertView.title = title
        self.alertView.message = message
        targetVC.present(self.alertView, animated: true, completion: nil)
    }
    
    func showAlertView(targetVC : UIViewController) {
        targetVC.present(self.alertView, animated: true, completion: nil)
    }
    
    func setTitle(title: String){
        self.alertView.title = title
    }
    
    func setMessage(message: String){
        self.alertView.message = message
    }
    
}

