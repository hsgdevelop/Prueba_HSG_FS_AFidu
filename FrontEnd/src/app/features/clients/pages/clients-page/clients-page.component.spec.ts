import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { ClientsPageComponent } from './clients-page.component';
import { ClientApiService } from '../../../../core/services/client-api.service';
import { LoggerService } from '../../../../core/services/logger.service';

class ClientApiServiceMock {
  getClients = jasmine.createSpy().and.returnValue(of([]));
  createClient = jasmine.createSpy().and.returnValue(of({}));
  advancedSearch = jasmine.createSpy().and.returnValue(of([]));
  exportCsv = jasmine.createSpy().and.returnValue(of(new Blob()));
}

class LoggerServiceMock {
  info = jasmine.createSpy();
}

describe('ClientsPageComponent', () => {
  let component: ClientsPageComponent;
  let fixture: ComponentFixture<ClientsPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ClientsPageComponent],
      providers: [
        { provide: ClientApiService, useClass: ClientApiServiceMock },
        { provide: LoggerService, useClass: LoggerServiceMock }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(ClientsPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should load clients on init', () => {
    expect(component.clients).toEqual([]);
  });

  it('should open create modal when clicking new button', () => {
    const button: HTMLButtonElement = fixture.nativeElement.querySelector('.toolbar-actions .primary');
    button.click();
    fixture.detectChanges();

    expect(component.showCreateModal).toBeTrue();
  });
});
