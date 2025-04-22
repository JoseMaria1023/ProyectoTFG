import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ConciertoService {
  private apiUrl = 'http://localhost:9000/api/conciertos'; // URL del backend

  constructor(private http: HttpClient) {}

  crearConcierto(concierto: any): Observable<any> {
    return this.http.post(this.apiUrl, concierto);
  }

  obtenerConciertos(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  actualizarConcierto(id: number, concierto: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/${id}`, concierto);
  }

  eliminarConcierto(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
  obtenerConciertosPorArtista(artistaId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/artista/${artistaId}`);
  }

  // Obtiene todos los asientos de un concierto
  obtenerAsientosPorConcierto(conciertoId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/${conciertoId}/asientos`);
  }
}
