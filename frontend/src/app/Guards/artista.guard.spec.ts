import { TestBed } from '@angular/core/testing';
import { CanActivateFn } from '@angular/router';

import { artistaGuard } from './artista.guard';

describe('artistaGuard', () => {
  const executeGuard: CanActivateFn = (...guardParameters) => 
      TestBed.runInInjectionContext(() => artistaGuard(...guardParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeGuard).toBeTruthy();
  });
});
