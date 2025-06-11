import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChatAsistenteComponent } from './chat-asistente.component';

describe('ChatAsistenteComponent', () => {
  let component: ChatAsistenteComponent;
  let fixture: ComponentFixture<ChatAsistenteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ChatAsistenteComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ChatAsistenteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
