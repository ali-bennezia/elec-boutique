import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddModifyCardPageComponent } from './add-modify-card-page.component';

describe('AddModifyCardPageComponent', () => {
  let component: AddModifyCardPageComponent;
  let fixture: ComponentFixture<AddModifyCardPageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AddModifyCardPageComponent]
    });
    fixture = TestBed.createComponent(AddModifyCardPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
