import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchProductsPageComponent } from './search-products-page.component';

describe('SearchProductsPageComponent', () => {
  let component: SearchProductsPageComponent;
  let fixture: ComponentFixture<SearchProductsPageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SearchProductsPageComponent]
    });
    fixture = TestBed.createComponent(SearchProductsPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
