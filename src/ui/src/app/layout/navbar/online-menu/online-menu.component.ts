import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/service/auth.service';

@Component({
  selector: 'app-online-menu',
  templateUrl: './online-menu.component.html',
  styleUrls: ['./online-menu.component.css'],
})
export class OnlineMenuComponent {
  constructor(public authService: AuthService, private router: Router) {}

  onClickLogout = (e: Event) => {
    this.authService.logout();
    this.router.navigate(['connexion']);
  };
}
