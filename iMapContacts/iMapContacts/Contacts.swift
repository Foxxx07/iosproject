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
    
    override func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return contacts.names.count
    }
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell", for: indexPath)
        let row = indexPath.row
        cell.textLabel?.text = contacts.names[row]
        
        
        return cell
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
    }
}
