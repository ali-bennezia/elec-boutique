import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { NgIconsModule } from '@ng-icons/core';
import { ionPersonOutline } from '@ng-icons/ionicons';

import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { NavbarComponent } from './layout/navbar/navbar.component';
import { FooterComponent } from './layout/footer/footer.component';
import { MatMenuModule } from '@angular/material/menu';

import { OfflineMenuComponent } from './layout/navbar/offline-menu/offline-menu.component';
import { SigninPageComponent } from './page/user/signin-page/signin-page.component';
import { RegisterPageComponent } from './page/user/register-page/register-page.component';
import { RouterModule } from '@angular/router';
import APP_ROUTES from './routing/routes';
import { HomePageComponent } from './page/home-page/home-page.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    FooterComponent,
    OfflineMenuComponent,
    SigninPageComponent,
    RegisterPageComponent,
    HomePageComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    MatIconModule,
    MatButtonModule,
    MatInputModule,
    MatMenuModule,
    NgIconsModule.withIcons({ ionPersonOutline }),
    RouterModule.forRoot(APP_ROUTES),
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
