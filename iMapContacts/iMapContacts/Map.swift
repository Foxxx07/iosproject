//
//  Acceuil.swift
//  iMapContacts
//
//  Created by m2sar on 27/04/17.
//  Copyright © 2017 m2sar. All rights reserved.
//

import Foundation
import UIKit
import MapKit

class Map: UIViewController, MKMapViewDelegate {

    
    @IBOutlet weak var viewMap: MKMapView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        viewMap.setUserTrackingMode(MKUserTrackingMode.follow, animated: true)
    
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
       
    }
    
    func mapView(_ mapView: MKMapView, didUpdate userLocation: MKUserLocation) {
        let center = CLLocationCoordinate2D(latitude: userLocation.coordinate.latitude, longitude: userLocation.coordinate.longitude)
        let width = 30.0
        let height = 30.0
        let region = MKCoordinateRegionMakeWithDistance(center, width, height)
        mapView.setRegion(region,animated : true)
        
    }
}

