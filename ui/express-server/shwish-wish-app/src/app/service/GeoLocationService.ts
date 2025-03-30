import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { UserGeoLocationResponseDto } from '../model/UserGeoLocationResponseDto';

@Injectable({
    providedIn: 'root',
})
export class GeolocationService {
    url: string = "https://api.kvapps.in/nginx/shwish-wish";
    // url: string = "http://localhost:8080";

    constructor(private http: HttpClient) { }

    getCurrentLocation(): Promise<GeolocationPosition> {
        return new Promise((resolve, reject) => {
            if ('geolocation' in navigator) {
                navigator.geolocation.getCurrentPosition(resolve, reject);
            } else {
                reject(new Error('Geolocation is not supported by your browser.'));
            }
        });
    }

    // This function validates the coords from backend.
    getUserGeoLocationDetails(location: any): Observable<UserGeoLocationResponseDto> {
        console.log("Calling getUserGeoLocationDetails for location - " + location);
        return this.http.post<UserGeoLocationResponseDto>(this.url + "/user-geo-location/validate", location)
            .pipe(
                catchError(this.handleError<UserGeoLocationResponseDto>(
                    'getUserGeoLocationDetails', new UserGeoLocationResponseDto(false, 0.0, 0.0)))
            );
    }

    private handleError<T>(operation = 'operation', result?: T) {
        return (error: any): Observable<T> => {

            console.error(error); // log to console instead

            this.log(`${operation} failed: ${error.message}`);

            // Let the app keep running by returning an empty result.
            return of(result as T);
        };
    }

    /** Log a HeroService message with the MessageService */
    private log(message: string) {
        console.log(message);
    }
}