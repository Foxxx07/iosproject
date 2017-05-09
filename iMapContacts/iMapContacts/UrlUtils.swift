//
//  UrlUtils.swift
//  iMapContacts
//
//  Created by m2sar on 04/05/17.
//  Copyright © 2017 m2sar. All rights reserved.
//

import Foundation
import UIKit

class UrlUtils {
    func sendToServ(httpMethod : HTTPMETHOD ,collection : String, urlComponents : URLComponents ) {
        
        var request = URLRequest(url: URL(string:"http://www.google.fr\(collection)")!)
        request.httpMethod = httpMethod.rawValue
        
        guard let parameters = urlComponents.query else {return}
        request.httpBody = parameters.data(using: .ascii)
        request.setValue("application/x-www-form-urlencoded", forHTTPHeaderField: "Content-Type")
        
        let task = URLSession.shared.dataTask(with: request, completionHandler: {(data,response,error) in })
        
        print( request.httpBody)
        print(urlComponents.url)
        
        task.resume()
        
    }
}
