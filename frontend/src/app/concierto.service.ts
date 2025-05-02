import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ConciertoService {
  private apiUrl = 'http://localhost:9000/api/conciertos'; // URL del backend

  constructor(private http: HttpClient) {}

  private getAuthHeaders(): HttpHeaders {
    const token = sessionStorage.getItem('token');
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json',
    });
  }

  crearConcierto(concierto: any): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.post(this.apiUrl, concierto, { headers });
  }

  obtenerConciertos(): Observable<any[]> {
    const headers = this.getAuthHeaders();
    return this.http.get<any[]>(this.apiUrl, { headers });
  }

  actualizarConcierto(id: number, concierto: any): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.put(`${this.apiUrl}/${id}`, concierto, { headers });
  }

  eliminarConcierto(id: number): Observable<void> {
    const headers = this.getAuthHeaders();
    return this.http.delete<void>(`${this.apiUrl}/${id}`, { headers });
  }

  obtenerConciertosPorArtista(artistaId: number): Observable<any[]> {
    const headers = this.getAuthHeaders();
    return this.http.get<any[]>(`${this.apiUrl}/artista/${artistaId}`, { headers });
  }

  filtrarConciertos(params: any): Observable<any[]> {
    const headers = this.getAuthHeaders();
    let httpParams = new HttpParams();
    if (params.artistaId) httpParams = httpParams.set('artistaId', params.artistaId);
    if (params.fechaDesde) httpParams = httpParams.set('fechaDesde', params.fechaDesde);
    if (params.fechaHasta) httpParams = httpParams.set('fechaHasta', params.fechaHasta);
    if (params.estado) httpParams = httpParams.set('estado', params.estado);

    return this.http.get<any[]>(`${this.apiUrl}/filtrar`, { params: httpParams, headers });
  }

  obtenerAsientosPorConcierto(conciertoId: number): Observable<any[]> {
    const headers = this.getAuthHeaders();
    return this.http.get<any[]>(`${this.apiUrl}/${conciertoId}/asientos`, { headers });
  }
}
