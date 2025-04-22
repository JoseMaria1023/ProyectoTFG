import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CrearRecintoComponent } from './crear-recinto.component';

describe('CrearRecintoComponent', () => {
  let component: CrearRecintoComponent;
  let fixture: ComponentFixture<CrearRecintoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CrearRecintoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CrearRecintoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
