// confetti.service.ts
import { Injectable } from '@angular/core';
import confetti from 'canvas-confetti';

@Injectable({
  providedIn: 'root',
})
export class ConfettiService {
  constructor() {}

  launchConfetti() {
    confetti({
      particleCount: 100,
      spread: 70,
      origin: { y: 0.6 },
    });
  }
}
