import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { IonHeader, IonToolbar, IonTitle, IonContent } from '@ionic/angular/standalone';
import { Geolocation, WatchPositionCallback } from '@capacitor/geolocation';
import { Functions, getFunctions, httpsCallable } from "firebase/functions";
import {
  CollectionReference,
  DocumentReference,
  Firestore,
  collection,
  doc,
  setDoc
} from '@angular/fire/firestore';
import { FirestoreService } from '../services/firestore.service';

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
})
export class HomePage implements OnInit {

  positionWatchId: any;
  position: GeolocationPosition  | undefined;

  constructor(private changeDetector: ChangeDetectorRef, private firestore: FirestoreService) { }

  ngOnInit(): void {
    const options : PositionOptions = {
        enableHighAccuracy: true,
        timeout: 5000,
        maximumAge: 0,
    }
    const callback: WatchPositionCallback = (res: any) => {
      console.log(res);
      this.position = res as GeolocationPosition ;
      const maPosition = {
        lat: this.position.coords.latitude,
        long: this.position.coords.longitude,
        time: this.position.timestamp,
        user: 'test'
      }
      this.firestore.save(maPosition);
    };
    Geolocation.watchPosition(options, callback);
  }

  getCurrentPosition() {

    if (this.positionWatchId) {
      Geolocation.clearWatch({id: this.positionWatchId});
      this.positionWatchId = null;
    }

    this.positionWatchId = Geolocation.watchPosition({timeout: 30000, enableHighAccuracy: true}, (result: any) => {
        this.position = result;
        this.changeDetector.detectChanges();
        Geolocation.clearWatch({id: this.positionWatchId});
        this.positionWatchId = null;
    });
  }


}
