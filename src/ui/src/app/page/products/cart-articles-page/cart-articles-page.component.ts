import { Component } from '@angular/core';
import { CardInboundDTO } from 'src/app/data/payment/dto/inbound/card-inbound-dto';
import { ArticleService } from 'src/app/service/article.service';

@Component({
  selector: 'app-cart-articles-page',
  templateUrl: './cart-articles-page.component.html',
  styleUrls: ['./cart-articles-page.component.css'],
})
export class CartArticlesPageComponent {
  selectedCard: CardInboundDTO | null = null;
  constructor(public articleService: ArticleService) {}
}
