import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { tap } from 'rxjs';
import { UserRegisterOutboundDTO } from 'src/app/data/service/auth/dto/outbound/user-register-outbound-dto';
import { AuthService } from 'src/app/service/auth.service';
import { environment } from 'src/environments/environment';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-register-page',
  templateUrl: './register-page.component.html',
  styleUrls: ['./register-page.component.css'],
})
export class RegisterPageComponent implements OnInit {
  group!: FormGroup;
  constructor(
    builder: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private _snackbar: MatSnackBar
  ) {
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
      businessName: ['', [Validators.required]],
      businessAddress: builder.group({
        street: ['', [Validators.required]],
        city: ['', [Validators.required]],
        zipCode: ['', [Validators.required]],
        country: ['', [Validators.required]],
      }),
      isProvider: [false],
      acceptLegals: [false, [Validators.requiredTrue]],
    });
  }

  ngOnInit(): void {
    this.toggleProviderFields(false);
  }

  toggleProviderFields(val: boolean) {
    if (val) {
      this.group.get('businessName')?.enable();
      this.group.get('businessAddress')?.enable();
    } else {
      this.group.get('businessName')?.disable();
      this.group.get('businessAddress')?.disable();
    }
  }

  onToggleIsProvider = (val: boolean) => {
    this.toggleProviderFields(val);
  };

  getDTO(): UserRegisterOutboundDTO {
    let dto = this.group.value;
    delete dto['acceptLegals'];
    return dto;
  }

  displaySnackbar(msg: string) {
    this._snackbar.open(msg, 'Close');
  }

  handleError(errCode: number) {
    switch (errCode) {
      case 400:
        this.displaySnackbar('Requête invalide.');
        break;
      case 409:
        this.displaySnackbar(
          "Nom d'utilisateur ou adresse e-mail déjà utilisée."
        );
        break;
      default:
        this.displaySnackbar('Erreur serveur interne.');
        break;
    }
  }
  loading: boolean = false;
  onSubmit = (e: Event) => {
    e.preventDefault();

    this.loading = true;
    this.authService
      .register(this.getDTO())
      .pipe(
        tap((_) => {
          this.loading = false;
        })
      )
      .subscribe((result) => {
        if (result.success) {
          this.router.navigate(['connexion'], {
            queryParams: { inscription: true },
          });
        } else {
          this.handleError(result.statusCode);
        }
      });
  };
}
