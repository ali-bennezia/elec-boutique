import { HttpClient } from '@angular/common/http';
import { Component, OnDestroy, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { MatTabChangeEvent } from '@angular/material/tabs';
import { UserProfileInboundDTO } from 'src/app/data/service/auth/dto/inbound/user-profile-inbound-dto';
import { AuthService } from 'src/app/service/auth.service';
import { MediaUtils } from 'src/app/utils/media-utils';
import { environment } from 'src/environments/environment';
import {
  DialogAuthDialog,
  DialogAuthData,
} from './auth-dialog/dialog-auth-dialog';
import { UserProfileOutboundDTO } from 'src/app/data/service/auth/dto/outbound/user-profile-outbound-dto';

import { Subscription } from 'rxjs';
import { tap } from 'rxjs/operators';

@Component({
  selector: 'app-profile-page',
  templateUrl: './profile-page.component.html',
  styleUrls: ['./profile-page.component.css'],
})
export class ProfilePageComponent implements OnInit, OnDestroy {
  generalProfile: UserProfileInboundDTO | null = null;

  loading: boolean = false;

  loadTab(i: number) {
    this.loading = true;
    switch (i) {
      case 0:
        this.toggleProviderFields(this.authService.isProvider);
        this.authService.getProfile().subscribe((result) => {
          if (result.success) {
            this.generalProfile = result.data;
            console.log(this.generalProfile);
          } else {
          }
          this.loading = false;
        });
        break;
      case 1:
        break;
      default:
        break;
    }
  }

  onTabChanged(ev: MatTabChangeEvent) {
    console.log(ev);
    switch (ev.index) {
      case 0:
        break;
      case 1:
        break;
      default:
        break;
    }
  }
  mediaUtils = MediaUtils;

  group!: FormGroup;
  constructor(
    private http: HttpClient,
    public authService: AuthService,
    builder: FormBuilder
  ) {
    this.group = builder.group({
      email: [''],
      password: [''],
      firstName: [''],
      lastName: [''],
      address: builder.group({
        street: [''],
        city: [''],
        zipCode: [''],
        country: [''],
      }),
      businessName: [''],
      businessAddress: builder.group({
        street: [''],
        city: [''],
        zipCode: [''],
        country: [''],
      }),
    });
  }

  toggleProviderFields(val: boolean) {
    if (val) {
      this.group.get('businessName')?.enable();
      this.group.get('businessAddress')?.enable();
    } else {
      this.group.get('businessName')?.disable();
      this.group.get('businessAddress')?.disable();
    }
  }

  getDto(
    authPassword: string,
    authPasswordConfirmation: string
  ): UserProfileOutboundDTO {
    let dto = this.group.value;
    for (let p in dto) {
      if (
        (typeof dto[p] == 'string' || dto[p] instanceof String) &&
        dto[p].trim() == ''
      )
        dto[p] = null;
    }
    dto['authPassword'] = authPassword;
    dto['authPasswordConfirmation'] = authPasswordConfirmation;

    return dto;
  }

  getPhotoDto(
    authPassword: string,
    authPasswordConfirmation: string,
    file: File | null
  ): FormData {
    let formData: FormData = new FormData();
    formData.append('authPassword', authPassword);
    formData.append('authPasswordConfirmation', authPasswordConfirmation);
    if (file != null) formData.append('profilePhoto', file);

    return formData;
  }

  updateProfile = (authPassword: string, authPasswordConfirmation: string) => {
    this.loading = true;
    this.authService
      .updateProfile(this.getDto(authPassword, authPasswordConfirmation))
      .subscribe((res) => {
        this.loading = false;

        if (res.success) {
          this.loadTab(0);
        } else {
        }
      });
  };

  updateProfilePhoto = (
    authPassword: string,
    authPasswordConfirmation: string,
    file: File | null
  ) => {
    this.loading = true;
    this.authService
      .updateProfilePhoto(
        this.getPhotoDto(authPassword, authPasswordConfirmation, file)
      )
      .subscribe((res) => {
        this.loading = false;

        if (res.success) {
          this.loadTab(0);
        } else {
        }
      });
  };

  readonly dialog = inject(MatDialog);

  generalAuthConfirmationSubscription: Subscription | null = null;
  generalAuthConfirmationUnsubscribe() {
    if (this.generalAuthConfirmationSubscription != null)
      this.generalAuthConfirmationSubscription.unsubscribe();
  }
  openGeneralAuthConfirmationDialog() {
    this.generalAuthConfirmationUnsubscribe();

    let d: MatDialogRef<DialogAuthDialog, DialogAuthData> =
      this.dialog.open(DialogAuthDialog);
    this.generalAuthConfirmationSubscription =
      d.componentInstance.onConfirmed$.subscribe((res) => {
        this.generalAuthConfirmationUnsubscribe();
        this.updateProfile(res.authPassword, res.authPasswordConfirmation);
      });
  }

  generalPhotoAuthConfirmationSubscription: Subscription | null = null;
  generalPhotoAuthConfirmationUnsubscribe() {
    if (this.generalPhotoAuthConfirmationSubscription != null)
      this.generalPhotoAuthConfirmationSubscription.unsubscribe();
  }
  openGeneralPhotoAuthConfirmationDialog(file: File) {
    this.generalPhotoAuthConfirmationUnsubscribe();

    let d: MatDialogRef<DialogAuthDialog, DialogAuthData> =
      this.dialog.open(DialogAuthDialog);
    this.generalAuthConfirmationSubscription =
      d.componentInstance.onConfirmed$.subscribe((res) => {
        this.generalPhotoAuthConfirmationUnsubscribe();
        this.updateProfilePhoto(
          res.authPassword,
          res.authPasswordConfirmation,
          file
        );
      });
  }

  onProfilePhotoInputFileChange(ev: Event) {
    let e = ev as any;
    let fileList = e.target.files;
    let file: File = [...fileList][0];
    this.openGeneralPhotoAuthConfirmationDialog(file);
    console.log(file);
  }

  ngOnInit(): void {
    this.loadTab(0);
  }

  ngOnDestroy(): void {
    this.generalAuthConfirmationUnsubscribe();
    this.generalPhotoAuthConfirmationUnsubscribe();
  }
}
