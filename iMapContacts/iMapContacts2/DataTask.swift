//
//  DataTask.swift
//  iMapContacts
//
//  Created by m2sar on 11/05/17.
//  Copyright © 2017 m2sar. All rights reserved.
//

import Foundation

class DataTask{
    var data : Data?
    var response : URLResponse?
    var error : Error?
    
    init(data : Data , response : URLResponse , error : Error) {
        self.data = data
        self.response = response
        self.error = error
    }
    init (){}
}

