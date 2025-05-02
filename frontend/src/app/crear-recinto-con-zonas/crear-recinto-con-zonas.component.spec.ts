import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CrearRecintoConZonasComponent } from './crear-recinto-con-zonas.component';

describe('CrearRecintoConZonasComponent', () => {
  let component: CrearRecintoConZonasComponent;
  let fixture: ComponentFixture<CrearRecintoConZonasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CrearRecintoConZonasComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CrearRecintoConZonasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
