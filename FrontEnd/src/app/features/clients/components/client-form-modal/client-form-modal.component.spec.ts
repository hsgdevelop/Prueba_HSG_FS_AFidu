import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ClientFormModalComponent } from './client-form-modal.component';

describe('ClientFormModalComponent', () => {
  let component: ClientFormModalComponent;
  let fixture: ComponentFixture<ClientFormModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ClientFormModalComponent]
    }).compileComponents();

    fixture = TestBed.createComponent(ClientFormModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should invalidate form when required fields are empty', () => {
    expect(component.form.invalid).toBeTrue();
  });

  it('should validate form with correct values', () => {
    component.form.setValue({
      name: 'Carlos Perez',
      phone: '3001234567',
      email: 'carlos@gmail.com',
      startDate: '2026-01-01',
      endDate: '2026-12-31'
    });

    expect(component.form.valid).toBeTrue();
  });

  it('should emit save event when form is valid', () => {
    spyOn(component.save, 'emit');

    component.form.setValue({
      name: 'Carlos Perez',
      phone: '3001234567',
      email: 'carlos@gmail.com',
      startDate: '2026-01-01',
      endDate: '2026-12-31'
    });

    component.submit();

    expect(component.save.emit).toHaveBeenCalled();
  });
});
