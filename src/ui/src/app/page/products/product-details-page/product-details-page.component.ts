import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute } from '@angular/router';
import { tap } from 'rxjs';
import { ProductInboundDTO } from 'src/app/data/products/dto/inbound/product-inbound-dto';
import { ArticleService } from 'src/app/service/article.service';
import { AuthService } from 'src/app/service/auth.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-product-details-page',
  templateUrl: './product-details-page.component.html',
  styleUrls: ['./product-details-page.component.css'],
})
export class ProductDetailsPageComponent {
  selectedMedia: number = 0;
  getSelectedMediaSrc() {
    if (
      this.selectedMedia < 0 ||
      this.selectedMedia >= this.product!.medias.length
    )
      return '/assets/images/no_media.png';
    else return this.getMediaSrc(this.product!.medias[this.selectedMedia]);
  }

  getMediaSrc(media: string) {
    return `${environment.backendUri}/api/medias/${media}`;
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

  fetchProduct(id: number) {
    this.loading = true;
    this.http
      .get<ProductInboundDTO>(`${environment.backendUri}/api/products/${id}`, {
        observe: 'response',
      })
      .pipe(
        tap((_) => {
          this.loading = false;
        })
      )
      .subscribe({
        next: (data) => {
          this.product = data.body;
        },
        error: (err) => {
          this.handleError(err.statusCode);
        },
      });
  }

  fetchMedias() {
    this.http
      .get<string[]>(
        `${environment.backendUri}/api/products/${this.product!.id}/medias`,
        { observe: 'response' }
      )
      .subscribe({
        next: (data) => {
          this.product!.medias = data.body ?? [];
        },
        error: (err) => {
          this.handleError(err.statusCode);
        },
      });
  }

  uploadMedia(file: File) {
    let formData: FormData = new FormData();
    formData.append('medias', file);
    this.http
      .post(
        `${environment.backendUri}/api/products/${this.product!.id}/medias`,
        formData,
        {
          observe: 'response',
          headers: {
            Authorization: `Bearer ${this.authService.session?.token}`,
          },
        }
      )
      .subscribe({
        next: (data) => {
          this.fetchMedias();
          if (
            this.selectedMedia < 0 ||
            this.selectedMedia >= this.product!.medias.length
          )
            this.selectedMedia = 0;
        },
        error: (err) => {
          this.handleError(err.statusCode);
        },
      });
  }

  onMediaInputFileChange(ev: Event) {
    let e = ev as any;
    let fileList = e.target.files;
    let file: File = [...fileList][0];
    this.uploadMedia(file);
  }

  onClickDeleteSelectedImage() {
    if (
      this.selectedMedia < 0 ||
      this.selectedMedia >= this.product!.medias.length
    )
      return;
    this.http
      .delete(
        `${environment.backendUri}/api/products/${this.product!.id}/medias/${
          this.product!.medias[this.selectedMedia]
        }`,
        {
          observe: 'response',
          headers: {
            Authorization: `Bearer ${this.authService.session?.token}`,
          },
        }
      )
      .subscribe({
        next: (data) => {
          this.fetchMedias();
          this.selectedMedia = this.selectedMedia - 1;
        },
        error: (err) => {
          this.handleError(err.statusCode);
        },
      });
  }

  onClickAddToCart(product: ProductInboundDTO) {
    this.articleService.addToCart(product);
    this.displaySnackbar('Article ajouté au panier.');
  }

  loading: boolean = false;
  product: ProductInboundDTO | null = null;
  constructor(
    activatedRoute: ActivatedRoute,
    private http: HttpClient,
    public authService: AuthService,
    private _snackbar: MatSnackBar,
    private articleService: ArticleService
  ) {
    activatedRoute.paramMap.subscribe((params) => {
      this.fetchProduct(Number(params.get('id')));
    });
  }
}
