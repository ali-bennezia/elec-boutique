import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatTabChangeEvent } from '@angular/material/tabs';
import { UserProfileInboundDTO } from 'src/app/data/service/auth/dto/inbound/user-profile-inbound-dto';
import { AuthService } from 'src/app/service/auth.service';
import { MediaUtils } from 'src/app/utils/media-utils';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-profile-page',
  templateUrl: './profile-page.component.html',
  styleUrls: ['./profile-page.component.css'],
})
export class ProfilePageComponent implements OnInit {
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

  ngOnInit(): void {
    this.loadTab(0);
  }
}
