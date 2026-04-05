import { Component, EventEmitter, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { AdvancedSearchParams } from '../../../../core/models/client.model';

@Component({
  selector: 'app-advanced-search-modal',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './advanced-search-modal.component.html',
  styleUrl: './advanced-search-modal.component.css'
})
export class AdvancedSearchModalComponent {
  @Output() cancel = new EventEmitter<void>();
  @Output() search = new EventEmitter<AdvancedSearchParams>();

  readonly form: FormGroup;

  constructor(private readonly fb: FormBuilder) {
    this.form = this.fb.nonNullable.group({
      name: [''],
      phone: [''],
      email: [''],
      startDate: [''],
      endDate: ['']
    });
  }

  submit(): void {
    const value = this.form.getRawValue();
    if (value.startDate && value.endDate && value.endDate < value.startDate) {
      this.form.controls['endDate'].setErrors({ invalidRange: true });
      return;
    }
    this.search.emit(value as AdvancedSearchParams);
  }

  clear(): void {
    this.form.reset({ name: '', phone: '', email: '', startDate: '', endDate: '' });
  }
}
