import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { GeolocationService } from './service/GeoLocationService';
import { timeout } from 'rxjs';


@Component({
  selector: 'app-root',
  imports: [RouterOutlet, MatCardModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'shwish-wish-app';
  location: { latitude: number; longitude: number } | null = null;
  error: string | null = null;
  isValidLocation: boolean = false;

  constructor(private geolocationService: GeolocationService) { }

  ngOnInit() {
    this.getLocation();

    console.log("Testing" + this.location);
    if (this.location != null
      && this.location.latitude != 0.0 && this.location?.longitude != 0.0) {

    }
  }

  getLocation() {
    this.geolocationService
      .getCurrentLocation()
      .then((position) => {
        this.location = {
          latitude: position.coords.latitude,
          longitude: position.coords.longitude,
        };

      //validate the location
      this.geolocationService.getUserGeoLocationDetails(this.location).subscribe(data => {
        this.isValidLocation = data.isValidLocation;
      });

        console.log("Found some location coords");
        this.error = null; // Clear any previous error
      })
      .catch((err) => {
        console.log("Error in some location coords");
        this.error = err.message;
        this.location = null; // Clear previous location
      });
  }
}
