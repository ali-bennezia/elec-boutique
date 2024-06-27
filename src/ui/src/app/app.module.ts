import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { FlexLayoutModule } from '@angular/flex-layout';
import { NgIconsModule } from '@ng-icons/core';
import {
  ionCartOutline,
  ionMenuOutline,
  ionPersonOutline,
} from '@ng-icons/ionicons';

import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { NavbarComponent } from './layout/navbar/navbar.component';
import { FooterComponent } from './layout/footer/footer.component';
import { MatMenuModule } from '@angular/material/menu';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatTabsModule } from '@angular/material/tabs';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatDialogModule } from '@angular/material/dialog';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatMomentDateModule } from '@angular/material-moment-adapter';
import { MatTableModule } from '@angular/material/table';

import { OfflineMenuComponent } from './layout/navbar/offline-menu/offline-menu.component';
import { SigninPageComponent } from './page/user/signin-page/signin-page.component';
import { RegisterPageComponent } from './page/user/register-page/register-page.component';
import { RouterModule } from '@angular/router';

import APP_ROUTES from './routing/routes';

import { HomePageComponent } from './page/home-page/home-page.component';
import { AboutPageComponent } from './page/legal/about-page/about-page.component';
import { PrivacyPolicyPageComponent } from './page/legal/privacy-policy-page/privacy-policy-page.component';
import { NotFoundPageComponent } from './page/not-found-page/not-found-page.component';
import { MobileMenuComponent } from './layout/navbar/mobile-menu/mobile-menu.component';
import { MAT_FORM_FIELD_DEFAULT_OPTIONS } from '@angular/material/form-field';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { OnlineMenuComponent } from './layout/navbar/online-menu/online-menu.component';
import { ProfilePageComponent } from './page/user/profile-page/profile-page.component';
import { UserDetailsPageComponent } from './page/user/user-details-page/user-details-page.component';
import { AddModifyCardPageComponent } from './page/user/card/add-modify-card-page/add-modify-card-page.component';
import { ManageProductsPageComponent } from './page/products/manage-products-page/manage-products-page.component';
import { AddModifyProductPageComponent } from './page/products/add-modify-product-page/add-modify-product-page.component';
import { ProductDetailsPageComponent } from './page/products/product-details-page/product-details-page.component';
import { CartMenuComponent } from './layout/navbar/cart-menu/cart-menu.component';
import { CartArticlesPageComponent } from './page/products/cart-articles-page/cart-articles-page.component';
import { PaymentPageComponent } from './page/products/payment-page/payment-page.component';
import { CardSelectorComponent } from './page/products/cart-articles-page/card-selector/card-selector.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    FooterComponent,
    OfflineMenuComponent,
    SigninPageComponent,
    RegisterPageComponent,
    HomePageComponent,
    AboutPageComponent,
    PrivacyPolicyPageComponent,
    NotFoundPageComponent,
    MobileMenuComponent,
    OnlineMenuComponent,
    ProfilePageComponent,
    UserDetailsPageComponent,
    AddModifyCardPageComponent,
    ManageProductsPageComponent,
    AddModifyProductPageComponent,
    ProductDetailsPageComponent,
    CartMenuComponent,
    CartArticlesPageComponent,
    PaymentPageComponent,
    CardSelectorComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    BrowserAnimationsModule,
    FlexLayoutModule,
    MatToolbarModule,
    MatIconModule,
    MatButtonModule,
    MatInputModule,
    MatMenuModule,
    MatCheckboxModule,
    MatSnackBarModule,
    MatTabsModule,
    MatGridListModule,
    MatDialogModule,
    MatProgressSpinnerModule,
    MatMomentDateModule,
    MatDatepickerModule,
    MatTableModule,
    NgIconsModule.withIcons({
      ionPersonOutline,
      ionMenuOutline,
      ionCartOutline,
    }),
    RouterModule.forRoot(APP_ROUTES),
    FormsModule,
    ReactiveFormsModule,
  ],
  providers: [
    {
      provide: MAT_FORM_FIELD_DEFAULT_OPTIONS,
      useValue: { appearance: 'outline' },
    },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
