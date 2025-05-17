// asiento.service.ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AsientoService {
  private apiUrl = 'http://localhost:9000/api/asientos';

  constructor(private http: HttpClient) {}

  private getAuthHeaders(): HttpHeaders {
    const token = sessionStorage.getItem('token');
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
  }

  getAllAsientos(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl, { headers: this.getAuthHeaders() });
  }

  crearAsiento(asiento: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, asiento, { headers: this.getAuthHeaders() });
  }

  actualizarAsiento(id: number, asiento: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}`, asiento, { headers: this.getAuthHeaders() });
  }

  eliminarAsiento(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${id}`, { headers: this.getAuthHeaders() });
  }

  getAsientoPorId(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`, { headers: this.getAuthHeaders() });
  }

  obtenerAsientosPorConcierto(conciertoId: number): Observable<any[]> {
    return this.http.get<any[]>(
      `${this.apiUrl}/concierto/${conciertoId}`,
      { headers: this.getAuthHeaders() }    // ← aquí antes no estabas enviando headers
    );
  }
}
