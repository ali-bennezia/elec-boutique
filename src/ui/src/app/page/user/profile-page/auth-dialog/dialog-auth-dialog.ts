import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MatInputModule } from '@angular/material/input';
import { Observable, Subject } from 'rxjs';

export interface DialogAuthData {
  authPassword: string;
  authPasswordConfirmation: string;
}

@Component({
  selector: 'dialog-auth-dialog',
  templateUrl: 'dialog-auth-dialog.html',
  styleUrls: ['dialog-auth-dialog.css'],
  standalone: true,
  imports: [MatDialogModule, MatButtonModule, MatInputModule, FormsModule],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class DialogAuthDialog {
  authPassword: string = '';
  authPasswordConfirmation: string = '';

  private onConfirmedSource: Subject<DialogAuthData> = new Subject();
  public onConfirmed$: Observable<DialogAuthData> =
    this.onConfirmedSource.asObservable();

  onConfirmed() {
    this.onConfirmedSource.next({
      authPassword: this.authPassword,
      authPasswordConfirmation: this.authPasswordConfirmation,
    });
  }
}
