import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CrearArtistaComponent } from './crear-artista.component';

describe('CrearArtistaComponent', () => {
  let component: CrearArtistaComponent;
  let fixture: ComponentFixture<CrearArtistaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CrearArtistaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CrearArtistaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
