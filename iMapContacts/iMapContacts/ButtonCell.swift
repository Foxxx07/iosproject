
//
//  ButtonCell.swift
//  iMapContacts
//
//  Created by m2sar on 09/05/17.
//  Copyright © 2017 m2sar. All rights reserved.
//

import UIKit

class ButtonCell: UITableViewCell {
    var contact = Contacts()
    
    var addFriend: UIButton
    let redColor = UIColor(red: 255/255.0, green: 59/255.0, blue: 48/255.0, alpha: 1.0)
    let greenColor = UIColor(red: 76/255.0, green: 217/255.0, blue: 100/255.0, alpha: 1.0)
    @IBOutlet weak var rowLabel: UILabel!
    var tapAction: ((UITableViewCell) -> Void)?
    
    func buttonTap(_ sender: AnyObject) {
        tapAction?(self)
        if (addFriend.backgroundColor == redColor) {
            addFriend.backgroundColor = greenColor
            addFriend.setTitle("Ajouter en ami", for: .normal)
        }
        else if (addFriend.backgroundColor == greenColor) {
            addFriend.setTitle("Supprimer", for: .normal)
            addFriend.backgroundColor = redColor
        }
        else {
            
        }
        
    }
    
    override init(style: UITableViewCellStyle, reuseIdentifier: String?) {
        addFriend = UIButton()
        addFriend.setTitleColor(UIColor.white, for: .normal)
        addFriend.bounds = CGRect(x: 0, y: 0, width: 140, height: 36)
        addFriend.layer.cornerRadius = 5
        
        super.init(style: style, reuseIdentifier: reuseIdentifier)
        
        self.addFriend.addTarget(self, action: #selector(buttonTap(_:)), for: .touchUpInside)
        self.accessoryView = addFriend
        
    }
    
    required init(coder aDecoder: NSCoder) {
        addFriend = UIButton()
        addFriend.setTitleColor(UIColor.white, for: .normal)
        addFriend.bounds = CGRect(x: 0, y: 0, width: 140, height: 36)
        addFriend.layer.cornerRadius = 5
        
        super.init(coder: aDecoder)!
        
        self.addFriend.addTarget(self, action: #selector(buttonTap(_:)), for: .touchUpInside)
        self.accessoryView = addFriend
    }

    
    
}

