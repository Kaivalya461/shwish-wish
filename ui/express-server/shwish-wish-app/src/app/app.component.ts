import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { GeolocationService } from './service/GeoLocationService';
import { ContentService } from './service/ContentService';
import { DecryptionService } from './service/DecryptionService';
import { timeout } from 'rxjs';
import { CommonModule } from '@angular/common';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { FormsModule } from '@angular/forms';
import {MatButtonModule} from '@angular/material/button';


@Component({
  selector: 'app-root',
  imports: [RouterOutlet,
    MatCardModule,
    CommonModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatFormFieldModule,
    MatInputModule,
    FormsModule,
    MatButtonModule],
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

  selectedDate: Date | null = new Date();

  msg1: string = '';
  msg2: string = '';

  constructor(private geolocationService: GeolocationService, private contentService: ContentService,
    private decryptionService: DecryptionService) {
  }

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
                let resultArray = data.qna.split('_');
                let key = this.getQNAKey();
                for (let i = 0; i < resultArray.length; i++) {
                  this.qnas[i] = this.decryptionService.decrypt(resultArray[i], key);
                }
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
        if(data.msg != null) {
          this.contentMsgReceived = true;
          let messages:string[] = data.msg.split('_');
          this.msg1 = messages[0];
          this.msg2 = messages[1];
        } else {
          alert("Incorrect Answers!!");
        }
      });
    }
  }

  getQNAKey(): any {
    if(this.location == null) {
      return 'InvalidKey';
    }

    const key1 = this.location.latitude.toString().replace(".", "");
    const key2 = this.location.longitude.toString().replace(".", "");
    const part1 = key1.substring(0,4);
    const part2 = key2.substring(0,4);

    return part1 + part2;
  }

  getMSGKey(): any {
    if(this.answers == null) {
      return 'InvalidMsgKey';
    }

    return this.answers.join('');
  }
}
