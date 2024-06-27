import { HttpClient } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ProductInboundDTO } from 'src/app/data/products/dto/inbound/product-inbound-dto';
import {
  APP_CATEGORIES,
  APP_SORTING_OPTIONS,
  SearchService,
} from 'src/app/service/search.service';
import { environment } from 'src/environments/environment';

import { tap } from 'rxjs/operators';
import { ProductPageInboundDTO } from 'src/app/data/products/dto/inbound/product-page-inbound-dto';

@Component({
  selector: 'app-search-products-page',
  templateUrl: './search-products-page.component.html',
  styleUrls: ['./search-products-page.component.css'],
})
export class SearchProductsPageComponent implements OnInit, OnDestroy {
  public appCategories: string[][] = APP_CATEGORIES;
  public appSortingOptions: string[][] = APP_SORTING_OPTIONS;

  articles: ProductPageInboundDTO | null = null;
  loading: boolean = false;

  group!: FormGroup;
  constructor(
    private http: HttpClient,
    private _snackbar: MatSnackBar,
    builder: FormBuilder,
    public searchService: SearchService
  ) {
    this.group = builder.group({
      miprice: [null],
      mxprice: [null],
      mieval: [null],
      mxeval: [null],
      sortby: [null],
      sortorder: [null],
    });
  }

  getUrlParamString() {
    let params: URLSearchParams = new URLSearchParams();
    let vals = this.group.value;
    for (let p in vals) {
      if (vals[p] != null) {
        params.append(p, vals[p]);
      }
    }
    let str = params.toString();
    let res = str == '' ? '' : `?${str}`;
    return res;
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

  displaySnackbar(msg: string) {
    this._snackbar.open(msg, 'Close');
  }

  onInput = (ev: Event) => {
    this.fetchArticles();
  };

  fetchArticles = () => {
    if (this.loading) return;
    this.loading = true;
    this.http
      .get<ProductPageInboundDTO>(
        `${environment.backendUri}/api/products${this.getUrlParamString()}`,
        {
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
          this.articles = data.body ?? null;
        },
        error: (err) => {
          this.handleError(err.statusCode);
        },
      });
  };

  ngOnInit(): void {
    this.fetchArticles();
  }

  ngOnDestroy(): void {}
}
