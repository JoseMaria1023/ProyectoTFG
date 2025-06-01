import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ZonaService {
  private apiUrl = 'http://localhost:9000/api/zonas'; // URL del backend

  constructor(private http: HttpClient) {}

  private getAuthHeaders(): HttpHeaders {
    const token = sessionStorage.getItem('token');
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
  }

  // Crear zona
  crearZona(zona: any): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.post(this.apiUrl, zona, { headers });
  }

  // Obtener todas las zonas
  TraerZonas(): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.get(this.apiUrl, { headers });
  }

  // Actualizar zona
  actualizarZona(id: number, zona: any): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.put(`${this.apiUrl}/${id}`, zona, { headers });
  }

  TraerZonaPorId(id: number): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.get<any>(`${this.apiUrl}/${id}`, { headers });
  }
  // Eliminar zona
  eliminarZona(id: number): Observable<void> {
    const headers = this.getAuthHeaders();
    return this.http.delete<void>(`${this.apiUrl}/${id}`, { headers });
  }
   getZonaPorConcierto(conciertoId: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/concierto/${conciertoId}`);
  }
}
