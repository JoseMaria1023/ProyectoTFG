import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ElegirConciertoComponent } from './elegir-concierto.component';

describe('ElegirConciertoComponent', () => {
  let component: ElegirConciertoComponent;
  let fixture: ComponentFixture<ElegirConciertoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ElegirConciertoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ElegirConciertoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
