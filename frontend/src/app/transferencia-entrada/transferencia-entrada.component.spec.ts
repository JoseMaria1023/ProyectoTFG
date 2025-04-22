import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TransferenciaEntradaComponent } from './transferencia-entrada.component';

describe('TransferenciaEntradaComponent', () => {
  let component: TransferenciaEntradaComponent;
  let fixture: ComponentFixture<TransferenciaEntradaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TransferenciaEntradaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TransferenciaEntradaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
