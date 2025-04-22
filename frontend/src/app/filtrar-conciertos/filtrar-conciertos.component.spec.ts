import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FiltrarConciertosComponent } from './filtrar-conciertos.component';

describe('FiltrarConciertosComponent', () => {
  let component: FiltrarConciertosComponent;
  let fixture: ComponentFixture<FiltrarConciertosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FiltrarConciertosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FiltrarConciertosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
