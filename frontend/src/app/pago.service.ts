import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PagoService {
  private apiUrl = 'http://localhost:9000/api/pagos';

  constructor(private http: HttpClient) {}

  crearPago(pago: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, pago);
  }

  TraerPagos(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }
}
