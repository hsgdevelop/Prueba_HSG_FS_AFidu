import { Component, EventEmitter, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CreateClientRequest } from '../../../../core/models/client.model';

@Component({
  selector: 'app-client-form-modal',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './client-form-modal.component.html',
  styleUrl: './client-form-modal.component.css'
})
export class ClientFormModalComponent {
  @Output() cancel = new EventEmitter<void>();
  @Output() save = new EventEmitter<CreateClientRequest>();

  readonly form: FormGroup;

  constructor(private readonly fb: FormBuilder) {
    this.form = this.fb.nonNullable.group({
      name: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(100)]],
      phone: ['', [Validators.required, Validators.pattern(/^[0-9]{7,15}$/)]],
      email: ['', [Validators.required, Validators.email]],
      startDate: ['', Validators.required],
      endDate: ['', Validators.required]
    });
  }

  submit(): void {
    this.form.markAllAsTouched();
    if (this.form.invalid) {
      return;
    }

    const value = this.form.getRawValue();
    if (value.endDate < value.startDate) {
      this.form.controls['endDate'].setErrors({ invalidRange: true });
      return;
    }

    this.save.emit(value as CreateClientRequest);
  }
}
