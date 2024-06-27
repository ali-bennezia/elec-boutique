import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DATE_FORMATS } from '@angular/material/core';
import * as moment from 'moment';
import { Moment } from 'moment';
import { MatDatepicker } from '@angular/material/datepicker';
import { CardOutboundDTO } from 'src/app/data/payment/dto/outbound/card-outbound-dto';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { AuthService } from 'src/app/service/auth.service';

import { tap } from 'rxjs/operators';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { validateBasis } from '@angular/flex-layout';

export const MY_FORMATS = {
  parse: {
    dateInput: 'MM/YYYY',
  },
  display: {
    dateInput: 'MM/YYYY',
    monthYearLabel: 'MMM YYYY',
    dateA11yLabel: 'LL',
    monthYearA11yLabel: 'MMMM YYYY',
  },
};

@Component({
  selector: 'app-add-modify-card-page',
  templateUrl: './add-modify-card-page.component.html',
  styleUrls: ['./add-modify-card-page.component.css'],
  providers: [{ provide: MAT_DATE_FORMATS, useValue: MY_FORMATS }],
})
export class AddModifyCardPageComponent {
  loading: boolean = false;
  group!: FormGroup;
  id: number | null = null;
  constructor(
    builder: FormBuilder,
    private http: HttpClient,
    private authService: AuthService,
    private _snackbar: MatSnackBar,
    private router: Router,
    activatedRoute: ActivatedRoute
  ) {
    this.group = builder.group({
      code: ['', [Validators.required]],
      ccv: [null, [Validators.required]],
      expirationDate: [moment(), [Validators.required]],
      firstName: ['', [Validators.required]],
      lastName: ['', [Validators.required]],
      address: builder.group({
        street: ['', [Validators.required]],
        city: ['', [Validators.required]],
        zipCode: ['', [Validators.required]],
        country: ['', [Validators.required]],
      }),
    });
    activatedRoute.queryParamMap.subscribe((params) => {
      if (params.has('id')) {
        this.id = Number(params.get('id'));
      } else this.id = null;
    });
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

  getDto(): CardOutboundDTO {
    let dto = this.group.value;
    if ('expirationDate' in dto)
      dto['expirationDateTime'] = dto.expirationDate.valueOf();
    delete dto['expirationDate'];

    return dto;
  }

  onSubmit = (ev: Event) => {
    let dto = this.getDto();

    if (this.id == null) {
      this.http
        .post(`${environment.backendUri}/api/users/cards`, dto, {
          observe: 'response',
          headers: {
            Authorization: `Bearer ${this.authService.session!.token}`,
            'Content-Type': 'application/json',
          },
        })
        .pipe(
          tap((_) => {
            this.loading = false;
          })
        )
        .subscribe({
          next: (data) => {
            this.router.navigate(['/profil'], { queryParams: { tab: 1 } });
          },
          error: (err) => {
            this.handleError(err.statusCode);
          },
        });
    } else {
      this.http
        .patch(`${environment.backendUri}/api/users/cards/${this.id}`, dto, {
          observe: 'response',
          headers: {
            Authorization: `Bearer ${this.authService.session!.token}`,
            'Content-Type': 'application/json',
          },
        })
        .pipe(
          tap((_) => {
            this.loading = false;
          })
        )
        .subscribe({
          next: (data) => {
            this.router.navigate(['/profil'], {
              queryParams: { tab: 1 },
            });
          },
          error: (err) => {
            this.handleError(err.statusCode);
          },
        });
    }
  };

  setMonthAndYear(
    normalizedMonthAndYear: Moment,
    datepicker: MatDatepicker<Moment>
  ) {
    const ctrlValue = this.group.get('expirationDate')!.value ?? moment();
    ctrlValue.month(normalizedMonthAndYear.month());
    ctrlValue.year(normalizedMonthAndYear.year());
    this.group.get('expirationDate')!.setValue(ctrlValue);
    datepicker.close();
  }
}
