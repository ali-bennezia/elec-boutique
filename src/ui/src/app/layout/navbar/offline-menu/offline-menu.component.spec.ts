import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OfflineMenuComponent } from './offline-menu.component';

describe('OfflineMenuComponent', () => {
  let component: OfflineMenuComponent;
  let fixture: ComponentFixture<OfflineMenuComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [OfflineMenuComponent]
    });
    fixture = TestBed.createComponent(OfflineMenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
