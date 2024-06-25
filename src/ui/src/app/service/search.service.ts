import { Injectable } from '@angular/core';
import { Observable, Subject, debounceTime, distinctUntilChanged } from 'rxjs';
import SearchData from '../data/service/search/search-data-interface';

@Injectable({
  providedIn: 'root',
})
export class SearchService {
  constructor() {}

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
}

const APP_CATEGORIES: string[][] = [
  ['bureautique', 'Bureautique'],
  ['fpgas', 'FPGAs'],
];
export { APP_CATEGORIES };
