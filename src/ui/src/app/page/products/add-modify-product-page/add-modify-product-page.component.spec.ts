import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddModifyProductPageComponent } from './add-modify-product-page.component';

describe('AddModifyProductPageComponent', () => {
  let component: AddModifyProductPageComponent;
  let fixture: ComponentFixture<AddModifyProductPageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AddModifyProductPageComponent]
    });
    fixture = TestBed.createComponent(AddModifyProductPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
