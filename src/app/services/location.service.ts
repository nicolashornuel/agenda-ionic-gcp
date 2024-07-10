import { Injectable } from '@angular/core';
import { CallbackID, Geolocation } from '@capacitor/geolocation';
import { Observable, Subject } from 'rxjs';
import { FirestoreService } from './firestore.service';

export interface Location {
  lat: number,
  lng: number,
  time: number,
  user: string,
  date: string
}

@Injectable({
  providedIn: 'root'
})
export class LocationService extends FirestoreService {

  private location$: Subject<Location> = new Subject<Location>();

  private options: PositionOptions = {
    enableHighAccuracy: true, //High accuracy mode (such as GPS, if available). default false.
    timeout: 5000, //The maximum wait time in milliseconds for location updates. default 10000.
    maximumAge: 0, //The maximum age in milliseconds of a possible cached position that is acceptable to return. default 0.
  }

  constructor() {
    super("locations");
  }

  public get get$(): Observable<Location> {
    return this.location$.asObservable();
  }

  public init(): Promise<CallbackID> {
   return Geolocation.watchPosition(this.options, position => this.callback(position));
  }

  private callback(position: any): void {
    const location = this.mapper(position);
    this.location$.next(location);
    //this.save(location);
  };

  private mapper(position: GeolocationPosition): Location {
    return {
      lat: position.coords.latitude,
      lng: position.coords.longitude,
      time: position.timestamp,
      user: 'user',
      date: new Date(position.timestamp).toJSON()
    }
  }

}
