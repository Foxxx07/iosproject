//
//  UrlUtils.swift
//  iMapContacts
//
//  Created by m2sar on 04/05/17.
//  Copyright © 2017 m2sar. All rights reserved.
//

import Foundation

class UrlUtils {
    let url = "http://www.google.fr"
    
    func sendToServ(httpMethod : String , collection : String , data : URLComponents ) {
        
        var request = URLRequest(url: URL(string: url+collection)!)
        request.httpMethod = httpMethod
    
        guard let parameters = data.query else { return }
        request.httpBody = parameters.data(using: .ascii)
        request.setValue("application/x-www-form-urlencoded", forHTTPHeaderField: "Content-Type")
        let task = URLSession.shared.dataTask(with: request, completionHandler: {(data,response,error) in })
    
        task.resume()
        
        
        
    }
    
}
