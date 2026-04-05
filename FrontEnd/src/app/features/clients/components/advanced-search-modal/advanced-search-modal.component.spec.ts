import { ComponentFixture, TestBed } from '@angular/core/testing';
import { AdvancedSearchModalComponent } from './advanced-search-modal.component';

describe('AdvancedSearchModalComponent', () => {
  let component: AdvancedSearchModalComponent;
  let fixture: ComponentFixture<AdvancedSearchModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdvancedSearchModalComponent]
    }).compileComponents();

    fixture = TestBed.createComponent(AdvancedSearchModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should emit search when date range is valid', () => {
    spyOn(component.search, 'emit');

    component.form.setValue({
      name: 'Ana',
      phone: '3001234567',
      email: 'ana@example.com',
      startDate: '2026-01-01',
      endDate: '2026-12-31'
    });

    component.submit();

    expect(component.search.emit).toHaveBeenCalled();
  });

  it('should set invalidRange when end date is before start date', () => {
    component.form.setValue({
      name: '',
      phone: '',
      email: '',
      startDate: '2026-12-31',
      endDate: '2026-01-01'
    });

    component.submit();

    expect(component.form.controls['endDate'].errors?.['invalidRange']).toBeTrue();
  });
});
