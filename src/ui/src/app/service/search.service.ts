import { Injectable } from '@angular/core';
import { Observable, Subject, debounceTime, distinctUntilChanged } from 'rxjs';
import SearchData from '../data/service/search/search-data-interface';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class SearchService {
  constructor(private router: Router) {}

  onSearchSource: Subject<SearchData> = new Subject();
  onSearch$: Observable<SearchData> = this.onSearchSource
    .asObservable()
    .pipe(distinctUntilChanged(), debounceTime(300));

  private triggerObservable() {
    this.onSearchSource.next({
      category: this._lastCategory,
      query: this._lastQuery,
    });
  }

  private _lastCategory: string = '';
  private _lastQuery: string = '';

  get lastCategory(): string {
    return this._lastCategory;
  }

  get lastQuery(): string {
    return this._lastQuery;
  }

  emitCategory(categ: string) {
    this._lastCategory = categ;
    this.triggerObservable();
  }
  emitQuery(query: string) {
    this._lastQuery = query;
    this.triggerObservable();
  }

  submitSearch() {
    this.router.navigate(['/produits', 'chercher'], {
      queryParams: {
        query: this._lastQuery,
        categories: JSON.stringify([this._lastCategory]),
      },
    });
  }
}

const APP_SORTING_OPTIONS = [
  ['name', 'Nom'],
  ['price', 'Prix'],
  ['average_note', 'Note moyenne'],
];

const APP_CATEGORIES: string[][] = [
  ['bureautique', 'Bureautique'],
  ['fpgas', 'FPGAs'],
  ['microcontroleurs', 'Microcontrôleurs'],
  ['signaux-telecommunications', 'Signaux et télécommunications'],
  ['equipement-outillages', 'Equipement et outillages'],
  ['industrie', 'Industrie'],
  ['sciences-recherches', 'Sciences et recherches'],
];
export { APP_CATEGORIES, APP_SORTING_OPTIONS };
