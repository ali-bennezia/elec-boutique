import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-register-page',
  templateUrl: './register-page.component.html',
  styleUrls: ['./register-page.component.css'],
})
export class RegisterPageComponent {
  group!: FormGroup;
  constructor(builder: FormBuilder) {
    this.group = builder.group({
      username: [
        '',
        [
          Validators.required,
          Validators.minLength(6),
          Validators.maxLength(60),
        ],
      ],
      email: [
        '',
        [
          Validators.required,
          Validators.email,
          Validators.minLength(3),
          Validators.maxLength(254),
        ],
      ],
      firstName: [
        '',
        [
          Validators.required,
          Validators.minLength(2),
          Validators.maxLength(50),
        ],
      ],
      lastName: [
        '',
        [
          Validators.required,
          Validators.minLength(2),
          Validators.maxLength(50),
        ],
      ],
      password: [
        '',
        [
          Validators.required,
          Validators.minLength(8),
          Validators.maxLength(128),
        ],
      ],
      address: builder.group({
        street: ['', [Validators.required]],
        city: ['', [Validators.required]],
        zipCode: ['', [Validators.required]],
        country: ['', [Validators.required]],
      }),
      businessName: [''],
      businessAddress: builder.group({
        street: ['', []],
        city: ['', []],
        zipCode: ['', []],
        country: ['', []],
      }),
      isProvider: [false],
      acceptLegals: [false, [Validators.requiredTrue]],
    });
  }

  onToggleIsProvider = (val: boolean) => {
    if (val) {
      this.group.get('businessName')?.enable();
      this.group.get('businessAddress')?.enable();
    } else {
      this.group.get('businessName')?.disable();
      this.group.get('businessAddress')?.disable();
    }
  };
}
