import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditarRecintoComponent } from './editar-recinto.component';

describe('EditarRecintoComponent', () => {
  let component: EditarRecintoComponent;
  let fixture: ComponentFixture<EditarRecintoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EditarRecintoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditarRecintoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
