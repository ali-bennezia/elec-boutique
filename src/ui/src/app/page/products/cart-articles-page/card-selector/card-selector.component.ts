import { HttpClient } from '@angular/common/http';
import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CardInboundDTO } from 'src/app/data/payment/dto/inbound/card-inbound-dto';
import { AuthService } from 'src/app/service/auth.service';
import { environment } from 'src/environments/environment';

import { tap } from 'rxjs/operators';

@Component({
  selector: 'app-card-selector',
  templateUrl: './card-selector.component.html',
  styleUrls: ['./card-selector.component.css'],
})
export class CardSelectorComponent implements OnInit {
  cards: CardInboundDTO[] = [];
  loading: boolean = false;
  displayCards: boolean = false;
  selectedCard: CardInboundDTO | null = null;
  @Output()
  onSelectedCard: EventEmitter<CardInboundDTO> =
    new EventEmitter<CardInboundDTO>();

  constructor(
    private http: HttpClient,
    private authService: AuthService,
    private _snackbar: MatSnackBar
  ) {}

  handleError(errCode: number) {
    switch (errCode) {
      case 400:
        this.displaySnackbar('Requête invalide.');
        break;
      default:
        this.displaySnackbar('Erreur serveur interne.');
        break;
    }
  }

  displaySnackbar(msg: string) {
    this._snackbar.open(msg, 'Close');
  }

  onClickCard(c: CardInboundDTO) {
    this.selectedCard = c;
    this.onSelectedCard.emit(c);
  }

  fetchCards() {
    this.http
      .get<CardInboundDTO[]>(`${environment.backendUri}/api/users/cards`, {
        observe: 'response',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${this.authService.session!.token}`,
        },
      })
      .pipe(
        tap((_) => {
          this.loading = false;
        })
      )
      .subscribe({
        next: (data) => {
          this.cards = data.body ?? [];
        },
        error: (err) => {
          this.handleError(err.statusCode);
        },
      });
  }

  ngOnInit(): void {
    this.fetchCards();
  }
}
