import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditarConciertoComponent } from './editar-concierto.component';

describe('EditarConciertoComponent', () => {
  let component: EditarConciertoComponent;
  let fixture: ComponentFixture<EditarConciertoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EditarConciertoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditarConciertoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
