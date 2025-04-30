import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { NotifyRequestDto } from '../model/NotifyRequestDto';

@Injectable({
    providedIn: 'root',
})
export class NotifyService {
    domainBaseUrl: string = "https://shwish-wish-api.kvapps.in";
    url:string = this.domainBaseUrl;

    constructor(private http: HttpClient) { }

    // This function send notification using BackEnd Notify API.
    sendNotification(notifyRequestDto: NotifyRequestDto): Observable<String> {
        console.log("Calling sendNotification for location - " + notifyRequestDto);
        return this.http.post<String>(this.url + "/notify/send", notifyRequestDto)
            .pipe(
                catchError(this.handleError<String>(
                    'sendNotification', "Send Notification Failed!"))
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