import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/service/auth.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { UserSignInOutboundDTO } from 'src/app/data/service/auth/dto/outbound/user-signin-outbound-dto';
import { tap } from 'rxjs';

@Component({
  selector: 'app-signin-page',
  templateUrl: './signin-page.component.html',
  styleUrls: ['./signin-page.component.css'],
})
export class SigninPageComponent {
  group!: FormGroup;
  constructor(
    builder: FormBuilder,
    private router: Router,
    private authService: AuthService,
    private _snackbar: MatSnackBar
  ) {
    this.group = builder.group({
      email: [
        '',
        [
          Validators.required,
          Validators.email,
          Validators.minLength(3),
          Validators.maxLength(254),
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
      rememberMe: [false],
    });
  }

  getDto(): UserSignInOutboundDTO {
    return this.group.value;
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
  onSubmit(e: Event) {
    this.loading = true;
    this.authService
      .login(this.getDto())
      .pipe(
        tap((_) => {
          this.loading = false;
        })
      )
      .subscribe((result) => {
        if (result.success) {
          this.router.navigate(['/'], { queryParams: { connexion: true } });
        } else {
          this.handleError(result.statusCode);
        }
      });
  }
}
