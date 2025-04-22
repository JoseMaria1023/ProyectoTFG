import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class RecintoService {
  private apiUrl = 'http://localhost:9000/api/recintos'; // URL de la API

  constructor(private http: HttpClient) {}

  // Método para crear un recinto
  crearRecinto(recinto: any): Observable<any> {
    return this.http.post(this.apiUrl, recinto);
  }

  // Método para obtener todos los recintos
  obtenerRecintos(): Observable<any> {
    return this.http.get(this.apiUrl);
  }
  
  // Método para actualizar un recinto
  actualizarRecinto(id: number, recinto: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/${id}`, recinto);
  }

  // Método para eliminar un recinto
  eliminarRecinto(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
