import { Routes } from '@angular/router';
import { HomePageComponent } from '../page/home-page/home-page.component';
import { SigninPageComponent } from '../page/user/signin-page/signin-page.component';
import { RegisterPageComponent } from '../page/user/register-page/register-page.component';

const APP_ROUTES: Routes = [
  {
    path: '',
    pathMatch: 'full',
    component: HomePageComponent,
  },
  {
    path: 'connexion',
    component: SigninPageComponent,
  },
  {
    path: 'inscription',
    component: RegisterPageComponent,
  },
];
export default APP_ROUTES;
