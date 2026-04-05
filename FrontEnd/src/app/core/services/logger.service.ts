import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class LoggerService {
  info(message: string, data?: unknown): void {
    console.log(`[INFO] ${new Date().toISOString()} - ${message}`, data ?? '');
  }

  error(message: string, data?: unknown): void {
    console.error(`[ERROR] ${new Date().toISOString()} - ${message}`, data ?? '');
  }
}
