import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class RecintoService {
  private apiUrl = 'http://localhost:9000/api/recintos'; // URL de la API

  constructor(private http: HttpClient) {}

  private getAuthHeaders(): HttpHeaders {
    const token = sessionStorage.getItem('token');
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json',
    });
  }

  // Método para crear un recinto
  crearRecinto(recinto: any): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.post(this.apiUrl, recinto, { headers });
  }

  // Método para obtener todos los recintos
  obtenerRecintos(): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.get(this.apiUrl, { headers });
  }
  
  // Método para actualizar un recinto
  actualizarRecinto(id: number, recinto: any): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.put(`${this.apiUrl}/${id}`, recinto, { headers });
  }

   obtenerRecintoPorId(id: number): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.get<any>(`${this.apiUrl}/${id}`, { headers });
  }
  // Método para eliminar un recinto
  eliminarRecinto(id: number): Observable<void> {
    const headers = this.getAuthHeaders();
    return this.http.delete<void>(`${this.apiUrl}/${id}`, { headers });
  }
}
