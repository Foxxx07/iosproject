//
//  Contacts.swift
//  iMapContacts
//
//  Created by m2sar on 03/05/17.
//  Copyright © 2017 m2sar. All rights reserved.
//


import UIKit
class ListeAmis: UIViewController, UITableViewDelegate, UITableViewDataSource {
    
    var contactList: [Amis] = []
    var alertView = AlertView.init(title: "Error", message: "", preferredStyle: UIAlertControllerStyle.alert)
    
    func createContact() {
        let contactA = Amis(n: "Gaëtan Maiuri", f: 1)
        let contactB = Amis(n: "Sujithan Karukanaran", f: 1)
        let contactC = Amis(n: "William Sergeant", f: 1)
        contactList.append(contactA)
        contactList.append(contactB)
        contactList.append(contactCs)
        //findcontacts()
    }

    let cellReuseIdentifier = "cell"
    

    @IBOutlet var tableView: UITableView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        createContact()
        // It is possible to do the following three things in the Interface Builder
        // rather than in code if you prefer.
        self.tableView.register(UITableViewCell.self, forCellReuseIdentifier: cellReuseIdentifier)
        tableView.delegate = self
        tableView.dataSource = self
    }
    
    // number of rows in table view
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.contactList.count
    }
    
    // create a cell for each table view row
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        let cell:UITableViewCell = self.tableView.dequeueReusableCell(withIdentifier: cellReuseIdentifier) as UITableViewCell!
        
        cell.textLabel?.text = self.contactList[indexPath.row].name
        
        return cell
    }
    
    // method to run when table view cell is tapped
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        print("You tapped cell number \(indexPath.row).")
    }
    
    // this method handles row deletion
    func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCellEditingStyle, forRowAt indexPath: IndexPath) {
        
        if editingStyle == .delete {
            
            // remove the item from the data model
            contactList.remove(at: indexPath.row)
            
            // delete the table view row
            tableView.deleteRows(at: [indexPath], with: .fade)
            
        } else if editingStyle == .insert {
            // Not used in our example, but if you were adding a new row, this is where you would do it.
        }
    }
    
    func tableView(_ tableView: UITableView, titleForDeleteConfirmationButtonForRowAt indexPath: IndexPath) -> String? {
        return "Supprimer"
    }
    
    func findContact() {
        
//        var urlComponents = URLComponents()
//        
//        let urlUtil = UrlUtils()
//        urlUtil.sendToServ(httpMethod: HTTPMETHOD.GET, collection: FRIENDS.ROOT.rawValue, urlComponents: urlComponents, callback: { (data, response, error) in
//            if let statusCode = response as? HTTPURLResponse {
//                if (statusCode.statusCode == 200) {
//                    do {
//                        if let data = data {
//                            let json =  try JSONSerialization.jsonObject(with: data, options: JSONSerialization.ReadingOptions.mutableContainers) as? NSDictionary
//                            print(json)
//                            
//                            if let value : Int = json?.value(forKey: "c") as! Int? {
//                                if (value == 0) {
//                                //add friends to contactList
//                                    
//                                    }
//                                }
//                        } else {
//                                    if ( value == 3){
//                                        self.alertView.setMessage(message: urlUtil.getMessage(code: 3))
//                                    }
//                                    if (value == 4) {
//                                        self.alertView.setMessage(message: urlUtil.getMessage(code: 4))
//                                    }
//                                    if (value == 5) {
//                                        self.alertView.setMessage(message: urlUtil.getMessage(code: 5))
//                                    }
//                                    self.alertView.showAlertView(targetVC: self)
//                                }
//                            }
//                        }
//                    
//                    catch let error{
//                        print(error)// Todo ?
//                    }
//                } else if (statusCode.statusCode == 404) {
//                    
//                }
//            } else {
//                // ...
//            }
//        })
//        
    
        

        
    }
   
}
