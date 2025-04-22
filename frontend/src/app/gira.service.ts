import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GiraService {
  private apiUrl = 'http://localhost:9000/api/giras';

  constructor(private http: HttpClient) {}

  getAllGiras(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  createGira(gira: any): Observable<any> {
    const body = {
      nombre: gira.nombre,
      descripcion: gira.descripcion,
      artistaId: gira.artistaId 
    };
    return this.http.post<any>(this.apiUrl, body);
  }

  deleteGira(id: number): Observable<void> {
    // Uso correcto de template literals
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  updateGira(id: number, gira: any): Observable<any> {
    // Uso correcto de template literals
    return this.http.put<any>(`${this.apiUrl}/${id}`, gira);
  }
}
