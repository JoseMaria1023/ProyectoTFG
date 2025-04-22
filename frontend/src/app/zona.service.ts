import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ZonaService {
  private apiUrl = 'http://localhost:9000/api/zonas'; // URL del backend

  constructor(private http: HttpClient) {}

  // Crear zona
  crearZona(zona: any): Observable<any> {
    return this.http.post(this.apiUrl, zona);
  }

  // Obtener todas las zonas
  obtenerZonas(): Observable<any> {
    return this.http.get(this.apiUrl);
  }

  // Actualizar zona
  actualizarZona(id: number, zona: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/${id}`, zona);
  }

  // Eliminar zona
  eliminarZona(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
