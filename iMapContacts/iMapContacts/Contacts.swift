//
//  Contacts.swift
//  iMapContacts
//
//  Created by m2sar on 03/05/17.
//  Copyright © 2017 m2sar. All rights reserved.
//

import Foundation



import UIKit
class Contacts: UITableViewController{
    
    var contacts = ContactList()
    let redColor = UIColor(red: 255/255.0, green: 59/255.0, blue: 48/255.0, alpha: 1.0)
    let greenColor = UIColor(red: 76/255.0, green: 217/255.0, blue: 100/255.0, alpha: 1.0)
    
    override func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return contacts.names.count
    }
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell", for: indexPath)
        
        if let cell = cell as? ButtonCell {
            let row = indexPath.row
            cell.textLabel?.text = contacts.names[row]
            
            if (contacts.friends[row] == false) {
                cell.addFriend.setTitle("Ajouter en ami", for: .normal)
                cell.addFriend.backgroundColor = greenColor
            }
            else {
                cell.addFriend.setTitle("Supprimer", for: .normal)
                cell.addFriend.backgroundColor = redColor
            }
        }
        return cell
    }
    

    
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }
}
