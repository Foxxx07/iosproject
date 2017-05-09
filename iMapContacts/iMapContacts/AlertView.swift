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
    
    func showAlertView(targetVC : UIViewController , title : String , message : String){
        let alertView = UIAlertController(title: title, message: message, preferredStyle: UIAlertControllerStyle.actionSheet)
//        alertView.addAction(UIAlertAction(title: title, style: .cancel, handler: {(action: UIAlertAction!) in
//            }))
        targetVC.present(alertView, animated: true, completion: nil)
        
    }
}
