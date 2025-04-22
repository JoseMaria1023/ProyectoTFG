import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MapaAsientosComponent } from './mapa-asientos.component';

describe('MapaAsientosComponent', () => {
  let component: MapaAsientosComponent;
  let fixture: ComponentFixture<MapaAsientosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MapaAsientosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MapaAsientosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
