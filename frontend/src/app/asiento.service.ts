import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AsientoService {
  private apiUrl = 'http://localhost:9000/api/asientos';

  constructor(private http: HttpClient) {}

  getAllAsientos(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  crearAsiento(asiento: any): Observable<any> {
    return this.http.post(this.apiUrl, asiento);
  }

  actualizarAsiento(id: number, asiento: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}`, asiento);
  }

  eliminarAsiento(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${id}`);
  }

  obtenerAsientosPorConcierto(conciertoId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/concierto/${conciertoId}`);
  }
}
