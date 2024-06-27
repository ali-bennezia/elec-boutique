import { Component } from '@angular/core';
import { ArticleService } from 'src/app/service/article.service';

@Component({
  selector: 'app-cart-menu',
  templateUrl: './cart-menu.component.html',
  styleUrls: ['./cart-menu.component.css'],
})
export class CartMenuComponent {
  constructor(public articleService: ArticleService) {}
}
