import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GestionarGiraComponent } from './gestionar-gira.component';

describe('GestionarGiraComponent', () => {
  let component: GestionarGiraComponent;
  let fixture: ComponentFixture<GestionarGiraComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GestionarGiraComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GestionarGiraComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
