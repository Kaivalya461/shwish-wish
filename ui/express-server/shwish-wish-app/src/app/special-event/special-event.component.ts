import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { ContentService } from '../service/ContentService';
import { DecryptionService } from '../service/DecryptionService';
import { NotifyService } from '../service/NotifyService';
import { NotifyRequestDto } from '../model/NotifyRequestDto';

@Component({
  selector: 'app-special-event',
  imports: [
    CommonModule,
    MatCardModule,
    MatButtonModule
  ],
  templateUrl: './special-event.component.html',
  styleUrl: './special-event.component.css'
})
export class SpecialEventComponent {
  isCollapsed = false;
  @Input() locationData: { latitude: number; longitude: number } | null = null;
  @Input() answersData: string[] = [];
  msg3: string = '';

  constructor(private contentService: ContentService,
    private decryptionService: DecryptionService,
    private notifyService: NotifyService) {
  }

  ngOnInit() {
    this.contentService
      .getSpecialEventMsgContent(this.locationData, this.answersData.join('_'))
      .subscribe(data => {
        if(data.msg != null) {
          let key = this.getMSGKey();
          this.msg3 = this.decryptionService.decrypt(data.msg, key+key);
        }
      });
  }

  notify() {
    // Send Notification
    let requestDto: NotifyRequestDto = {
      notificationSource: 'Special_Event_Notify_KV',
      lat: this.locationData?.latitude + '',
      lon: this.locationData?.longitude + '',
      answers: this.answersData.join('')
    }
    
    this.notifyService.sendNotification(requestDto).subscribe();
  }

  toggleCollapse() {
    this.isCollapsed = !this.isCollapsed;
  }

  getMSGKey(): any {
    if(this.answersData == null) {
      return 'InvalidMsgKey';
    }

    return this.answersData.join('');
  }
}