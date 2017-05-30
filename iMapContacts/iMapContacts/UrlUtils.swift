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
    let serveur =  "http://132.227.113.237:8081" // http://imapc.lessonsharing.fr
    
    private var map :[Int : String] = [0:"Succes",
                                       1:"Renseigner un nom",
                                       2:"E-mail déjà utilisé",
                                       3:"Renseigner un e-mail",
                                       4:"Renseigner un mot de passe",
                                       5:"Format d'email invalide",
                                       6:"Format de clé utilisateur invalide",
                                       7:"Format de clé héxadécimale invalide",
                                       8:"Aucun utilisateur",
                                       9:"Erreur de requête",
                                       10:"Erreur SQL",
                                       11:"Utilisateur non connecté"
                                       ]
    
    func sendToServ(httpMethod : HTTPMETHOD ,collection : String, urlComponents : URLComponents, callback : @escaping (_ data : Data?, _ response: URLResponse?, _ error: Error?) ->()) {
        var request = URLRequest(url: URL(string:"\(serveur)\(collection)")!)
        request.httpMethod = httpMethod.rawValue
        
        guard let parameters = urlComponents.query else {return  }
        request.httpBody = parameters.data(using: .ascii)
        request.setValue("application/x-www-form-urlencoded", forHTTPHeaderField: "Content-Type" )
        
        let session = URLSession.shared.dataTask(with: request, completionHandler: { (data, response, error) in
            
            callback(data, response, error)
        })
//        print(request.httpBody)
//        print(urlComponents.url)
        session.resume()
    
    }
    
    func getMessage(code : Int) -> String {
             if let message = map[code]{
                return message
            }
            else{
                return "Unknow code"
            }
        return "Unknow code"
    }
}

