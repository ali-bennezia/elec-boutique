import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ProductInboundDTO } from 'src/app/data/products/dto/inbound/product-inbound-dto';
import { AuthService } from 'src/app/service/auth.service';
import { environment } from 'src/environments/environment';

import { tap } from 'rxjs/operators';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-manage-products-page',
  templateUrl: './manage-products-page.component.html',
  styleUrls: ['./manage-products-page.component.css'],
})
export class ManageProductsPageComponent implements OnInit {
  displayedColumns: string[] = ['name', 'tags', 'price', 'actions'];

  loading: boolean = false;
  products: ProductInboundDTO[] = [];

  fetchProducts() {
    this.loading = true;
    this.http
      .get<ProductInboundDTO[]>(
        `${environment.backendUri}/api/products/users/${this.authService.session?.id}`,
        {
          headers: {
            Authorization: `Bearer ${this.authService.session?.id}`,
          },
          observe: 'response',
        }
      )
      .pipe(
        tap((_) => {
          this.loading = false;
        })
      )
      .subscribe({
        next: (data) => {
          this.products = data.body ?? [];
        },
        error: (err) => {
          this.handleError(err.statusCode);
        },
      });
  }

  handleError(errCode: number) {
    switch (errCode) {
      case 400:
        this.displaySnackbar('Requête invalide.');
        break;
      default:
        this.displaySnackbar('Erreur serveur interne.');
        break;
    }
  }

  onClickDeleteProduct(prod: ProductInboundDTO) {
    this.loading = true;
    this.http
      .delete(`${environment.backendUri}/api/products/${prod.id}`, {
        observe: 'response',
        headers: {
          Authorization: `Bearer ${this.authService.session!.token}`,
        },
      })
      .pipe(
        tap((_) => {
          this.loading = false;
        })
      )
      .subscribe({
        next: () => {
          this.products = this.products.filter((el) => el.id != prod.id);
        },
        error: (err) => {
          this.handleError(err.statusCode);
        },
      });
  }

  displaySnackbar(msg: string) {
    this._snackbar.open(msg, 'Close');
  }

  constructor(
    private http: HttpClient,
    private router: Router,
    private authService: AuthService,
    private _snackbar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.fetchProducts();
  }
}
