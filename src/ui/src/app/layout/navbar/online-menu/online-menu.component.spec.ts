import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OnlineMenuComponent } from './online-menu.component';

describe('OnlineMenuComponent', () => {
  let component: OnlineMenuComponent;
  let fixture: ComponentFixture<OnlineMenuComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [OnlineMenuComponent]
    });
    fixture = TestBed.createComponent(OnlineMenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
