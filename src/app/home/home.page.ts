import { Component, OnInit } from '@angular/core';
import { LocationService, Location } from '../services/location.service';
import { takeUntil } from 'rxjs';
import { DestroyService } from '../services/destroy.service';

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
})
export class HomePage implements OnInit {

  public location!: Location;

  constructor(private locationService: LocationService, private destroy$: DestroyService) { }

  ngOnInit(): void {
    this.locationService.get$
      .pipe(takeUntil(this.destroy$))
      .subscribe(location => this.location = location)
  }
}