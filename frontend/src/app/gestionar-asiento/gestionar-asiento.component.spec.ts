import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GestionarAsientoComponent } from './gestionar-asiento.component';

describe('GestionarAsientoComponent', () => {
  let component: GestionarAsientoComponent;
  let fixture: ComponentFixture<GestionarAsientoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GestionarAsientoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GestionarAsientoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
