import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GestionarArtistaComponent } from './gestionar-artista.component';

describe('GestionarArtistaComponent', () => {
  let component: GestionarArtistaComponent;
  let fixture: ComponentFixture<GestionarArtistaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GestionarArtistaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GestionarArtistaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
