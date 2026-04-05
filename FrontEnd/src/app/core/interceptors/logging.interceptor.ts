import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { tap } from 'rxjs';
import { LoggerService } from '../services/logger.service';

export const loggingInterceptor: HttpInterceptorFn = (req, next) => {
  const logger = inject(LoggerService);
  const started = performance.now();

  logger.info(`HTTP ${req.method} ${req.urlWithParams} - request`);

  return next(req).pipe(
    tap({
      next: () => {
        const duration = Math.round(performance.now() - started);
        logger.info(`HTTP ${req.method} ${req.urlWithParams} - response in ${duration} ms`);
      },
      error: (error) => {
        const duration = Math.round(performance.now() - started);
        logger.error(`HTTP ${req.method} ${req.urlWithParams} - error in ${duration} ms`, error);
      }
    })
  );
};
