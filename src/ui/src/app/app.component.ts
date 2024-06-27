import { Component, OnDestroy, OnInit } from '@angular/core';
import { AuthService } from './service/auth.service';
import { ArticleService } from './service/article.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent implements OnInit, OnDestroy {
  title = 'ui';

  constructor(
    private authService: AuthService,
    private articleService: ArticleService
  ) {}

  ngOnInit(): void {
    this.authService.fetchSession();
    this.articleService.fetchCart();
  }

  ngOnDestroy(): void {
    this.authService.saveSession();
    this.articleService.saveCart();
  }
}
