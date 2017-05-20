//
//  UrlUtils.swift
//  iMapContacts
//
//  Created by m2sar on 04/05/17.
//  Copyright © 2017 m2sar. All rights reserved.
//

import Foundation
<<<<<<< HEAD

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
    
=======
import UIKit

class UrlUtils {
    
    func sendToServ(httpMethod : HTTPMETHOD ,collection : String, urlComponents : URLComponents  ) -> DataTask? {
        
        let dataTask : DataTask? = DataTask()
        var request = URLRequest(url: URL(string:"http://www.google.fr\(collection)")!)
        request.httpMethod = httpMethod.rawValue
        
        guard let parameters = urlComponents.query else {return dataTask!}
        request.httpBody = parameters.data(using: .ascii)
        request.setValue("application/x-www-form-urlencoded", forHTTPHeaderField: "Content-Type" )
        
        let session = URLSession.shared.dataTask(with: request, completionHandler: { (data, response, error) in
            if let data = data { dataTask?.data = data }
            if let response = response { dataTask?.response = response }
            if let error = error{ dataTask?.error = error }
        })
        
//        print( request.httpBody)
//        print(urlComponents.url)
          session.resume()
          return dataTask
    }
    
    func convertToJsonObject(data : Data?) -> Any? {
        do {
            return try  JSONSerialization.jsonObject(with: data!, options: .mutableContainers)
        }
        catch let jsonError {
            print("problem to convert to json")
        }
        return nil
    }
>>>>>>> florent
}
