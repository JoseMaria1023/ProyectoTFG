import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditarGiraComponent } from './editar-gira.component';

describe('EditarGiraComponent', () => {
  let component: EditarGiraComponent;
  let fixture: ComponentFixture<EditarGiraComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EditarGiraComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditarGiraComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
