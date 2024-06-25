import { Component, OnDestroy, OnInit } from '@angular/core';
import { AuthService } from './service/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent implements OnInit, OnDestroy {
  title = 'ui';

  constructor(private authService: AuthService) {}

  ngOnInit(): void {
    this.authService.fetchSession();
  }

  ngOnDestroy(): void {
    this.authService.saveSession();
  }
}
