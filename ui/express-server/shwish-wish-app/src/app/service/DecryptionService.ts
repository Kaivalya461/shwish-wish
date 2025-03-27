import { Injectable } from '@angular/core';
import * as CryptoJS from 'crypto-js';

@Injectable({
    providedIn: 'root',
})
export class DecryptionService {
    decrypt(inputValue: string, key: string): string {
        try {
            const bytes = CryptoJS.AES.decrypt(inputValue, CryptoJS.enc.Utf8.parse(key), {
                mode: CryptoJS.mode.ECB,
                padding: CryptoJS.pad.Pkcs7
              });
            const result =  bytes.toString(CryptoJS.enc.Utf8); // Convert decrypted bytes to string
            return result;
          } catch (error) {
            console.error('Decryption failed:', error);
            return '';
          }
    }
}