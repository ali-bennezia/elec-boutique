import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageProductsPageComponent } from './manage-products-page.component';

describe('ManageProductsPageComponent', () => {
  let component: ManageProductsPageComponent;
  let fixture: ComponentFixture<ManageProductsPageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ManageProductsPageComponent]
    });
    fixture = TestBed.createComponent(ManageProductsPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
