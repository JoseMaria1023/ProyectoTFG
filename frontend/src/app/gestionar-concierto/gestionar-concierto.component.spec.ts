import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GestionarConciertoComponent } from './gestionar-concierto.component';

describe('GestionarConciertoComponent', () => {
  let component: GestionarConciertoComponent;
  let fixture: ComponentFixture<GestionarConciertoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GestionarConciertoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GestionarConciertoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
