import { Routes } from '@angular/router';
import { HomePageComponent } from '../page/home-page/home-page.component';
import { SigninPageComponent } from '../page/user/signin-page/signin-page.component';
import { RegisterPageComponent } from '../page/user/register-page/register-page.component';
import { AboutPageComponent } from '../page/legal/about-page/about-page.component';
import { PrivacyPolicyPageComponent } from '../page/legal/privacy-policy-page/privacy-policy-page.component';
import { NotFoundPageComponent } from '../page/not-found-page/not-found-page.component';
import { ProfilePageComponent } from '../page/user/profile-page/profile-page.component';
import { UserDetailsPageComponent } from '../page/user/user-details-page/user-details-page.component';
import { AddModifyCardPageComponent } from '../page/user/card/add-modify-card-page/add-modify-card-page.component';

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
  {
    path: 'profil/formulaire-carte',
    component: AddModifyCardPageComponent,
  },
  {
    path: 'profil',
    component: ProfilePageComponent,
  },
  {
    path: 'membre/:id',
    component: UserDetailsPageComponent,
  },
  {
    path: 'apropos',
    component: AboutPageComponent,
  },
  {
    path: 'politique-confidentialite',
    component: PrivacyPolicyPageComponent,
  },
  {
    path: '**',
    component: NotFoundPageComponent,
  },
];
export default APP_ROUTES;
