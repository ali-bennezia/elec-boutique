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

import { Observable, of } from 'rxjs';
import { switchMap, catchError, tap } from 'rxjs/operators';
import { AuthSessionInboundDto } from '../data/service/auth/dto/inbound/auth-session-inbound-dto';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(private http: HttpClient) {}

  private _session: AuthSessionInboundDto | null = null;

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
          this._session = resp.data;
          this.saveSession();
        })
      );
  }
}
