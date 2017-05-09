
//
//  ButtonCell.swift
//  iMapContacts
//
//  Created by m2sar on 09/05/17.
//  Copyright © 2017 m2sar. All rights reserved.
//

import UIKit

class ButtonCell: UITableViewCell {
    
    var tapAction: ((UITableViewCell) -> Void)?
    
    @IBOutlet weak var rowLabel: UILabel!
    @IBAction func buttonTap(_ sender: AnyObject) {
        tapAction?(self)
    }
    
  

}
