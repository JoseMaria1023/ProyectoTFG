import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GiraComponent } from './crear-gira.component';

describe('GiraComponent', () => {
  let component: GiraComponent;
  let fixture: ComponentFixture<GiraComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GiraComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GiraComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
