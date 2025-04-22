import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CrearConciertoComponent } from './crear-concierto.component';

describe('CrearConciertoComponent', () => {
  let component: CrearConciertoComponent;
  let fixture: ComponentFixture<CrearConciertoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CrearConciertoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CrearConciertoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
