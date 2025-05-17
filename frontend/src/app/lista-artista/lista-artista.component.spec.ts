import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListaArtistaComponent } from './lista-artista.component';

describe('ListaArtistaComponent', () => {
  let component: ListaArtistaComponent;
  let fixture: ComponentFixture<ListaArtistaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListaArtistaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListaArtistaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
