import { Component } from '@angular/core';
import { SearchService } from 'src/app/service/search.service';
import { APP_CATEGORIES } from 'src/app/service/search.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css'],
})
export class NavbarComponent {
  public categories: string[][] = APP_CATEGORIES;
  constructor(public searchService: SearchService) {}

  onSearchSubmit(e: Event) {
    e.preventDefault();
    let query = this.searchService.lastQuery;
    console.log(query);
  }
}
