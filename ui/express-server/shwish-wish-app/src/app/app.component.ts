import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { GeolocationService } from './service/GeoLocationService';
import { ContentService } from './service/ContentService';
import { timeout } from 'rxjs';
import { CommonModule } from '@angular/common';


@Component({
  selector: 'app-root',
  imports: [RouterOutlet, MatCardModule, CommonModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'shwish-wish-app';
  location: { latitude: number; longitude: number } | null = null;
  error: string | null = null;
  isValidLocation: boolean = false;
  contentMsgReceived: boolean = false;

  qnas: string[] = [];
  currentQnaIndex: number = 0;
  answers: string[] = [];
  // answerInput: string = '';

  constructor(private geolocationService: GeolocationService, private contentService: ContentService) { }

  ngOnInit() {
    this.getLocation();

    if (this.location != null
      && this.location.latitude != 0.0 && this.location?.longitude != 0.0) {
      console.log("Inside Valid Location");
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
          this.isValidLocation = data.validLocation;

          // get QNA Content
          if(data.validLocation) {
              this.contentService.getQnaContent(this.location).subscribe(data => {
                this.qnas = data.qna.split('_');
            });
          }
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

  // Get the current qna
  get currentQna(): string {
    return this.qnas[this.currentQnaIndex];
  }

  // Save answer and move to the next qna
  saveAnswer(answer: HTMLInputElement): void {
    this.answers[this.currentQnaIndex] = answer.value;
    answer.value = '';

    if (this.currentQnaIndex < this.qnas.length - 1) {
      this.currentQnaIndex++;
    } else {
      console.log('All answered');
      // Get Msg Content
      let answers = this.answers.join('_');
      this.contentService.getMsgContent(this.location, answers).subscribe(data => {
        //Return the Content to UI;
        //Decrypt
        if(data.msg != null) {
          this.contentMsgReceived = true;
        }
      });
    }
  }
}
