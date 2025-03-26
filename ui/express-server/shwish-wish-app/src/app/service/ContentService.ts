import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { UserGeoLocationResponseDto } from '../model/UserGeoLocationResponseDto';
import { ContentDto } from '../model/ContentDto';

@Injectable({
    providedIn: 'root',
})
export class ContentService {
    domainBaseUrl: string = "https://api.kvapps.in";

    constructor(private http: HttpClient) { }

    // This function gets QNA Content from backend.
    getQnaContent(location: any): Observable<ContentDto> {
        console.log("Calling getQnaContent for location - " + location);
        let lat = "lat=" + location.latitude;
        let lon = "lon=" + location.longitude;
        return this.http.get<ContentDto>(this.domainBaseUrl + "/nginx/shwish-wish/content/qna" + "?" + lat + "&" + lon)
            .pipe(
                catchError(this.handleError<ContentDto>(
                    'getQnaContent', new ContentDto()))
            );
    }

    // This function gets MSG Content from backend.
    getMsgContent(location: any, answers: string): Observable<ContentDto> {
        console.log("Calling getMsgContent for location - " + location);
        let lat = "lat=" + location.latitude;
        let lon = "lon=" + location.longitude;
        // let ans = "answers=" + answers;
        return this.http.get<ContentDto>(this.domainBaseUrl + "/nginx/shwish-wish/content/message" + "?" + lat + "&" + lon + "&ans1=asdf&ans2=asd")
            .pipe(
                catchError(this.handleError<ContentDto>(
                    'getMsgContent', new ContentDto()))
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