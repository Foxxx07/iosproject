//
//  Acceuil.swift
//  iMapContacts
//
//  Created by m2sar on 27/04/17.
//  Copyright © 2017 m2sar. All rights reserved.
//

import UIKit
import MapKit

class Acceuil: UIViewController, MKMapViewDelegate {
    var timer = Timer()
    var timer2 = Timer()
    var localUserLocation = CLLocation(latitude: MKUserLocation().coordinate.latitude, longitude: MKUserLocation().coordinate.longitude)
    var n = 0
    var updateLocalUserLocation = true
    
    @IBOutlet weak var viewMap: MKMapView!
    @IBOutlet weak var recentrerButton: UIButton!
    
    @IBAction func recentrer(_ sender: AnyObject) {
        viewMap.setUserTrackingMode(MKUserTrackingMode.follow, animated: true)
        
    }
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        viewMap.setUserTrackingMode(MKUserTrackingMode.follow, animated: true)
        
        timerWithTimeInrterval()	
    }
    
    override func didMove(toParentViewController parent: UIViewController?) {
        timerWithTimeInrterval()
    }
    
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        
    }
    
    func mapView(_ mapView: MKMapView, didUpdate userLocation: MKUserLocation) {
        let center = CLLocationCoordinate2D(latitude: userLocation.coordinate.latitude, longitude: userLocation.coordinate.longitude)
      
//        if (testDistance(location: CLLocation(center.latitude,center.longitude))){
//            if (updateLocalUserLocation){
//            localUserLocation = CLLocation(latitude: center.latitude, longitude: center.longitude)
//            }
//        }
        
        
        let width = 30.0
        let height = 30.0
        let region = MKCoordinateRegionMakeWithDistance(center, width, height)
        mapView.setRegion(region,animated : true)
    }
    
    func timerWithTimeInrterval(){
         timer = Timer.scheduledTimer(timeInterval: 1, target: self, selector:#selector(Acceuil.testDistance), userInfo: nil, repeats: true)
         timer2 = Timer.scheduledTimer(timeInterval: 5, target: self, selector:#selector(Acceuil.afficherContacts), userInfo: nil, repeats: true)
    }
    
    func testDistance(location : CLLocation) -> Bool{
        
//        let distanceInMeters = localUserLocation.distance(from: location)
//        if (distanceInMeters > 7 ){
//            
//            print(">7 : \(n)")
//            n = n+1
//        }
        return true
    }
    
    func afficherContacts(){
        
    }
    
}

