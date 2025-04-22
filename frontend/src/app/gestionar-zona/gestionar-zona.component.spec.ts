import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GestionarZonaComponent } from './gestionar-zona.component';

describe('GestionarZonaComponent', () => {
  let component: GestionarZonaComponent;
  let fixture: ComponentFixture<GestionarZonaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GestionarZonaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GestionarZonaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
