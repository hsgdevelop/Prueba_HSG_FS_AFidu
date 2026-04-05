import { TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { ClientApiService } from './client-api.service';

describe('ClientApiService', () => {
  let service: ClientApiService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()]
    });

    service = TestBed.inject(ClientApiService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should call get clients endpoint', () => {
    service.getClients('jgutierrez').subscribe();

    const request = httpMock.expectOne('http://localhost:8080/api/clients?sharedKey=jgutierrez');
    expect(request.request.method).toBe('GET');
    request.flush([]);
  });
});
