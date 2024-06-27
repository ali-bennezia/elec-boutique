import { Component } from '@angular/core';
import { ArticleService } from 'src/app/service/article.service';

@Component({
  selector: 'app-cart-articles-page',
  templateUrl: './cart-articles-page.component.html',
  styleUrls: ['./cart-articles-page.component.css'],
})
export class CartArticlesPageComponent {
  constructor(public articleService: ArticleService) {}
}
