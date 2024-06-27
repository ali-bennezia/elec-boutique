import { Component } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { HttpClient } from '@angular/common/http';
import { AuthService } from 'src/app/service/auth.service';
import { Router } from '@angular/router';
import { ActivatedRoute } from '@angular/router';
import { Validators } from '@angular/forms';
import { ProductOutboundDTO } from 'src/app/data/products/dto/outbound/product-outbound-dto';
import { environment } from 'src/environments/environment';

import { tap } from 'rxjs/operators';

@Component({
  selector: 'app-add-modify-product-page',
  templateUrl: './add-modify-product-page.component.html',
  styleUrls: ['./add-modify-product-page.component.css'],
})
export class AddModifyProductPageComponent {
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
      name: ['', [Validators.required]],
      description: ['', [Validators.required]],
      tags: ['', [Validators.required]],
      price: [null, [Validators.required, Validators.min(1)]],
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

  getDto(): ProductOutboundDTO {
    let dto = this.group.value;
    return dto;
  }

  onSubmit = (ev: Event) => {
    let dto = this.getDto();

    if (this.id == null) {
      this.http
        .post(`${environment.backendUri}/api/products`, dto, {
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
            this.router.navigate(['/produits', 'gestion']);
          },
          error: (err) => {
            this.handleError(err.statusCode);
          },
        });
    } else {
      this.http
        .patch(`${environment.backendUri}/api/products/${this.id}`, dto, {
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
            this.router.navigate(['/produits', 'gestion']);
          },
          error: (err) => {
            this.handleError(err.statusCode);
          },
        });
    }
  };
}
