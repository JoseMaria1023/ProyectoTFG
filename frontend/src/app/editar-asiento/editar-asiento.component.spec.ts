import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditarAsientoComponent } from './editar-asiento.component';

describe('EditarAsientoComponent', () => {
  let component: EditarAsientoComponent;
  let fixture: ComponentFixture<EditarAsientoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EditarAsientoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditarAsientoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
