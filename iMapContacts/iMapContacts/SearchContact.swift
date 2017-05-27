//
//  SearchContact.swift
//  iMapContacts
//
//  Created by m2sar on 11/05/17.
//  Copyright © 2017 m2sar. All rights reserved.
//

import UIKit

class SearchContact: UIViewController{
    
    @IBOutlet weak var searchBar: UISearchBar!
    @IBOutlet weak var tableView: UITableView!

    var searchActive : Bool = false
    var data = ["San Francisco","New York","San Jose","Chicago","Los Angeles","Austin","Seattle"]
    var filtered:[String] = []
    var identities = ["A", "B"]
     
     override func viewDidLoad() {
     super.viewDidLoad()
     }
    
//    override func viewDidLoad() {
//        super.viewDidLoad()
//        
//        /* Setup delegates */
//        tableView.delegate = self
//        tableView.dataSource = self
//        searchBar.delegate = self
//        
//    }
    
    func searchBarTextDidBeginEditing(_ searchBar: UISearchBar) {
        searchActive = true;
    }
    
    func searchBarTextDidEndEditing(_ searchBar: UISearchBar) {
        searchActive = false;
    }
    
    func searchBarCancelButtonClicked(_ searchBar: UISearchBar) {
        searchActive = false;
    }
    
    func searchBarSearchButtonClicked(_ searchBar: UISearchBar) {
        searchActive = false;
    }
    
    func searchBar(_ searchBar: UISearchBar, textDidChange searchText: String) {
        
        /*filtered = data.filter({ (text) -> Bool in
            let tmp: NSString = text as NSString
            let range = tmp.rangeOfString(searchText, options: NSString.CompareOptions.CaseInsensitiveSearch)
            return range.location != NSNotFound
        })
        if(filtered.count == 0){
            searchActive = false;
        } else {
            searchActive = true;
        }
        self.tableView.reloadData()*/
        func searchBar(_ searchBar: UISearchBar, textDidChange searchText: String) {
            
            filtered = data.filter({ (text: String) -> Bool in
                
                return text.range(of: searchText, options: String.CompareOptions.caseInsensitive) != nil
            })
            
            if(filtered.count == 0){
                searchActive = false
            } else {
                searchActive = true
            }
            self.tableView.reloadData()
        }
        print(">> Search \(searchText)")
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    
    private func numberOfSectionsInTableView(tableView: UITableView) -> Int {
        return 1
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        /*if(searchActive) {
            return filtered.count
        }
        return data.count;*/
        return 0
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "Cell", for: indexPath)
        
       /* if(searchActive){
            cell.searchBar.text = filtered[indexPath.row]
        } else {
            cell.searchBar.text = data[indexPath.row]
        }*/
        return cell
    }
    
    func findContacts() {
        let urlComponents = URLComponents()
        let dataTask = UrlUtils().sendToServ(httpMethod: HTTPMETHOD.GET, collection: USER.SEARCH.rawValue, urlComponents: urlComponents)
        UrlUtils().convertToJsonObject(data: dataTask?.data)
        
    }
}
