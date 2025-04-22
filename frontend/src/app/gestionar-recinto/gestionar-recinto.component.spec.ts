import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GestionarRecintoComponent } from './gestionar-recinto.component';

describe('GestionarRecintoComponent', () => {
  let component: GestionarRecintoComponent;
  let fixture: ComponentFixture<GestionarRecintoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GestionarRecintoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GestionarRecintoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
