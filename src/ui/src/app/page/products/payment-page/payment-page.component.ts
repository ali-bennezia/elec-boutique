import { HttpClient, HttpRequest } from '@angular/common/http';
import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ArticleService } from 'src/app/service/article.service';
import { AuthService } from 'src/app/service/auth.service';
import { environment } from 'src/environments/environment';

import { Observable, zip, tap } from 'rxjs';
import { HttpResponse } from '@angular/common/http';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-payment-page',
  templateUrl: './payment-page.component.html',
  styleUrls: ['./payment-page.component.css'],
})
export class PaymentPageComponent {
  constructor(
    activatedRoute: ActivatedRoute,
    private http: HttpClient,
    private authService: AuthService,
    private articleService: ArticleService,
    private router: Router,
    private _snackbar: MatSnackBar
  ) {
    activatedRoute.paramMap.subscribe((params) => {
      let cardId = Number(params.get('cardId'));
      this.proceed(cardId);
    });
  }

  loading: boolean = false;
  success: boolean = false;
  failed: boolean = false;
  proceed(cardId: number) {
    this.loading = true;
    let obs = [];
    for (let c of this.articleService.cart) {
      obs.push(
        this.http.post(
          `${environment.backendUri}/api/products/${c.id}/pay/${cardId}`,
          null,
          {
            observe: 'response',
            headers: {
              Authorization: `Bearer ${this.authService.session?.token}`,
            },
          }
        )
      );
    }
    let allObs: Observable<HttpResponse<Object>[]> = zip(...obs);
    allObs
      .pipe(
        tap((_) => {
          this.loading = false;
        })
      )
      .subscribe({
        next: () => {
          this.success = true;
          this.failed = false;
          this.articleService.clearCart();
        },
        error: (err) => {
          this.success = false;
          this.failed = false;
        },
      });
  }
}
