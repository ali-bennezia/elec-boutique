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
import { ManageProductsPageComponent } from '../page/products/manage-products-page/manage-products-page.component';
import { AddModifyProductPageComponent } from '../page/products/add-modify-product-page/add-modify-product-page.component';
import { ProductDetailsPageComponent } from '../page/products/product-details-page/product-details-page.component';
import { CartArticlesPageComponent } from '../page/products/cart-articles-page/cart-articles-page.component';
import { PaymentPageComponent } from '../page/products/payment-page/payment-page.component';
import { SearchProductsPageComponent } from '../page/products/search-products-page/search-products-page.component';

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
    path: 'produits/gestion',
    component: ManageProductsPageComponent,
  },
  {
    path: 'produits/formulaire',
    component: AddModifyProductPageComponent,
  },
  {
    path: 'produits/payer/:cardId',
    component: PaymentPageComponent,
  },
  {
    path: 'produits/chercher',
    component: SearchProductsPageComponent,
  },
  {
    path: 'mes-articles',
    component: CartArticlesPageComponent,
  },
  {
    path: 'produits/:id',
    component: ProductDetailsPageComponent,
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
