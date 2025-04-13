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
import { MatButtonModule } from '@angular/material/button';
import { ConfettiService } from './service/ConfettiService';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatDividerModule } from '@angular/material/divider';


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
    MatButtonModule,
    MatProgressSpinnerModule,
    MatDividerModule
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'shwish-wish-app';
  location: { latitude: number; longitude: number } | null = null;
  error: string | null = null;
  isValidLocation: boolean = false;
  contentMsgReceived: boolean = false;
  isCollapsed = false;

  showQna: boolean = true;
  qnas: string[] = [];
  currentQnaIndex: number = 0;
  answers: string[] = [];

  selectedDate: Date | null = new Date();
  currentYear = new Date().getFullYear();
  targetDate: Date | null = new Date(this.currentYear,4,22);
  backgroundImage: string = '';

  msg1: string = '';
  msg2: string = '';

  spinnerStatusText: string = '';
  showLandingCardSpinner: boolean = false;
  activityStatusText: string = '';

  constructor(private geolocationService: GeolocationService, private contentService: ContentService,
    private decryptionService: DecryptionService, private confettiService: ConfettiService) {
  }

  ngOnInit() {
    // Landing-Card Spinner and Text
    this.showLandingCardSpinner = true;
    this.spinnerStatusText = "Identifying User Location..."

    this.getLocation();
  }

  toggleCollapse() {
    this.isCollapsed = !this.isCollapsed;
  }

  getLocation() {
    this.geolocationService
      .getCurrentLocation()
      .then((position) => {
        this.location = {
          latitude: position.coords.latitude,
          longitude: position.coords.longitude,
        };

        // Check whether User provided permissions or not.
        // If granged, proceed with fetching data from backend.
        this.getLocationStatus();
      })
      .catch((err) => {
        console.log("Error in some location coords");
        if (err.message.includes("denied")) {
          this.disableSpinnerAndUpdateActivityText(
            "User Denied Location Access - Please reset your 'Location' permissions for this site & try again!"
          );
        }
        this.error = err.message;
        this.location = null; // Clear previous location
      });
  }

  private disableSpinnerAndUpdateActivityText(activityText: string) {
    this.showLandingCardSpinner = false;
    this.spinnerStatusText = '';
    this.activityStatusText = activityText;
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

      const currentDate = new Date();
      if (
        this.targetDate != null &&
        currentDate &&
        currentDate.getMonth() === this.targetDate.getMonth() &&
        currentDate.getDate() === this.targetDate.getDate()
      ) {
        this.confettiService.launchConfetti();
  
        //Img
        let answers = this.answers.join('_');
        this.contentService.getImgContent(this.location, answers).subscribe(data => {
          this.decryptAndSetBackgroundImage(data.img);
        });
  
        this.contentMsgReceived = false;
        this.showQna = false;
      } else {
        this.contentService.getMsgContent(this.location, answers).subscribe(data => {
          //Return the Content to UI;
          if(data.msg != null) {
            this.contentMsgReceived = true;
            this.showQna = false;
            let messages:string[] = data.msg.split('_');
            let key = this.getMSGKey();
            this.msg1 = this.decryptionService.decrypt(messages[0], key+key);
            this.msg2 = this.decryptionService.decrypt(messages[1], key+key);
          } else {
            //reset qna
            this.currentQnaIndex = 0;
            this.answers = [];
            alert("Incorrect Answers!!");
          }
        });
      }
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

  celebrate() {
    this.confettiService.launchConfetti();
  }

  onDateChange(event: any): void {
    const pickedDate = event.value; // Get the selected date

    // Check if the selected date matches your criteria
    const specificDate = this.targetDate // Replace with your target date (month is zero-based)
    if (
      specificDate != null &&
      pickedDate &&
      pickedDate.getMonth() === specificDate.getMonth() &&
      pickedDate.getDate() === specificDate.getDate()
    ) {
      this.confettiService.launchConfetti(); // Trigger confetti

      //Img
      let answers = this.answers.join('_');
      this.contentService.getImgContent(this.location, answers).subscribe(data => {
        this.decryptAndSetBackgroundImage(data.img);
      });

      //Collapse Div
      this.isCollapsed = true;
    }
  }

  setBackgroundImage(base64String: string): void {
    this.backgroundImage = `data:image/jpeg;base64,${base64String}`;
  }

  decryptAndSetBackgroundImage(encryptedString: string): void {
    const key = this.getMSGKey();
    this.backgroundImage = this.decryptionService.decrypt(encryptedString, key+key);
  }

  getLocationStatus() {
    this.geolocationService.getLocationPermission().then((status) => {
      if (status === 'denied') {
        this.disableSpinnerAndUpdateActivityText("Location Permissions not found. Please provide the Location Access!");
      } else if (status === 'granted') {
        //validate the location
        this.geolocationService.getUserGeoLocationDetails(this.location).subscribe(data => {
          this.isValidLocation = data.validLocation;
          this.disableSpinnerAndUpdateActivityText("");

          // get QNA Content
          if(data.validLocation) {
              this.contentService.getQnaContent(this.location).subscribe(data => {
                let resultArray = data.qna.split('_');
                let key = this.getQNAKey();
                for (let i = 0; i < resultArray.length; i++) {
                  this.qnas[i] = this.decryptionService.decrypt(resultArray[i], key+key);
                }
            });
          } else {
            this.showLandingCardSpinner = false;
            this.activityStatusText = "Invalid Location - Please try again and refresh the page!";
            alert("Invalid Location - Please try again and refresh the page!");
          }
        });

        console.log("Found some location coords");
        this.error = null; // Clear any previous error
      }
    }).catch((error) => {
      this.error = error; // Handle any errors
      console.error(error);
    });
  }
}
