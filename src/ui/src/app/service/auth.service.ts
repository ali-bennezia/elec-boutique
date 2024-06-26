import {
  HttpClient,
  HttpErrorResponse,
  HttpResponse,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthOperationResult } from '../data/service/auth/auth-operation-result';

import { environment } from 'src/environments/environment';

import { UserRegisterOutboundDTO } from '../data/service/auth/dto/outbound/user-register-outbound-dto';
import { UserSignInOutboundDTO } from '../data/service/auth/dto/outbound/user-signin-outbound-dto';
import { UserProfileInboundDTO } from '../data/service/auth/dto/inbound/user-profile-inbound-dto';

import { Observable, of } from 'rxjs';
import { switchMap, catchError, tap } from 'rxjs/operators';
import { AuthSessionInboundDto } from '../data/service/auth/dto/inbound/auth-session-inbound-dto';
import { UserProfileOutboundDTO } from '../data/service/auth/dto/outbound/user-profile-outbound-dto';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(private http: HttpClient) {}

  private _session: AuthSessionInboundDto | null = null;

  public get session(): AuthSessionInboundDto | null {
    return this._session;
  }

  public get isAnonymous(): boolean {
    return !this.isAuthenticated;
  }

  public get isAuthenticated(): boolean {
    return this._session != null;
  }

  public get isProvider(): boolean {
    return this.hasRole('ROLE_PROVIDER');
  }

  public get isAdmin(): boolean {
    return this.hasRole('ROLE_ADMIN');
  }

  hasRole(role: string) {
    return (
      (this.isAuthenticated && this.session?.roles?.includes(role)) ?? false
    );
  }

  fetchSession() {
    let s: string | null = localStorage.getItem('session');
    if (s != null) {
      this._session = JSON.parse(s);
    } else this._session = null;
  }

  saveSession() {
    if (this._session != null) {
      localStorage.setItem('session', JSON.stringify(this._session));
    } else {
      localStorage.removeItem('session');
    }
  }

  getProfilePhotoMediaUri() {
    return this.isAuthenticated && this._session?.profilePhotoMedia != null
      ? `${environment.backendUri}/api/medias/${this._session.profilePhotoMedia}`
      : '/assets/images/guest_user.png';
  }

  getProfile(): Observable<AuthOperationResult> {
    return this.http
      .get<UserProfileInboundDTO>(
        `${environment.backendUri}/api/users/profile`,
        {
          headers: {
            Authorization: `Bearer ${this.session?.token}`,
          },
          observe: 'response',
        }
      )
      .pipe(
        catchError((err) => {
          return of({
            success: false,
            statusCode: err.statusCode,
            data: err.body,
          });
        }),
        switchMap((resp) => {
          if (resp instanceof HttpResponse) {
            return of({
              success: true,
              statusCode: resp.status,
              data: resp.body,
            });
          } else return of(resp);
        })
      );
  }

  updateProfile(dto: UserProfileOutboundDTO): Observable<AuthOperationResult> {
    return this.http
      .patch(`${environment.backendUri}/api/users/profile`, dto, {
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${this.session?.token}`,
        },
        observe: 'response',
      })
      .pipe(
        catchError((err) => {
          return of({
            success: false,
            statusCode: err.statusCode,
            data: err.body,
          });
        }),
        switchMap((resp) => {
          if (resp instanceof HttpResponse) {
            return of({
              success: true,
              statusCode: resp.status,
              data: resp.body,
            });
          } else return of(resp);
        })
      );
  }

  register(dto: UserRegisterOutboundDTO): Observable<AuthOperationResult> {
    return this.http
      .post(`${environment.backendUri}/api/users/register`, dto, {
        headers: {
          'Content-Type': 'application/json',
        },
        observe: 'response',
      })
      .pipe(
        catchError((err) => {
          return of({
            success: false,
            statusCode: err.statusCode,
            data: err.body,
          });
        }),
        switchMap((resp) => {
          if (resp instanceof HttpResponse) {
            return of({
              success: true,
              statusCode: resp.status,
              data: resp.body,
            });
          } else return of(resp);
        })
      );
  }

  login(dto: UserSignInOutboundDTO): Observable<AuthOperationResult> {
    return this.http
      .post<AuthSessionInboundDto>(
        `${environment.backendUri}/api/users/signin`,
        dto,
        {
          headers: {
            'Content-Type': 'application/json',
          },
          observe: 'response',
        }
      )
      .pipe(
        catchError((err) => {
          return of({
            success: false,
            statusCode: err.statusCode,
            data: err.body,
          });
        }),
        switchMap((resp) => {
          if (resp instanceof HttpResponse) {
            return of({
              success: true,
              statusCode: resp.status,
              data: resp.body,
            });
          } else return of(resp);
        }),
        tap((resp) => {
          if (resp.success) {
            this._session = resp.data;
          } else {
            this._session = null;
          }
          this.saveSession();
        })
      );
  }

  logout() {
    this._session = null;
    this.saveSession();
  }
}
