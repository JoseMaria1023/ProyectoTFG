import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GestionarAsientoConciertoArtistaComponent } from './gestionar-asiento-concierto-artista.component';

describe('GestionarAsientoConciertoArtistaComponent', () => {
  let component: GestionarAsientoConciertoArtistaComponent;
  let fixture: ComponentFixture<GestionarAsientoConciertoArtistaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GestionarAsientoConciertoArtistaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GestionarAsientoConciertoArtistaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
