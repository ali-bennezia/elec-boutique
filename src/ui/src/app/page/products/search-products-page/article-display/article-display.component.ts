import { Component, Input } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ProductInboundDTO } from 'src/app/data/products/dto/inbound/product-inbound-dto';
import { ArticleService } from 'src/app/service/article.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-article-display',
  templateUrl: './article-display.component.html',
  styleUrls: ['./article-display.component.css'],
})
export class ArticleDisplayComponent {
  @Input()
  articleData!: ProductInboundDTO;

  constructor(
    private articleService: ArticleService,
    private _snackbar: MatSnackBar
  ) {}

  displaySnackbar(msg: string) {
    this._snackbar.open(msg, 'Close');
  }

  getMediaSrc(media: string | null) {
    return media == null
      ? '/assets/images/no_media.png'
      : `${environment.backendUri}/api/medias/${media}`;
  }

  onClickAddToCart() {
    this.articleService.addToCart(this.articleData);
    this.displaySnackbar('Article ajouté avec succès.');
  }
}
