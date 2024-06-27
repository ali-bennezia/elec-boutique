import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CartArticlesPageComponent } from './cart-articles-page.component';

describe('CartArticlesPageComponent', () => {
  let component: CartArticlesPageComponent;
  let fixture: ComponentFixture<CartArticlesPageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CartArticlesPageComponent]
    });
    fixture = TestBed.createComponent(CartArticlesPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
