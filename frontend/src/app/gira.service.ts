import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GiraService {
  private apiUrl = 'http://localhost:9000/api/giras';

  constructor(private http: HttpClient) {}

  private getAuthHeaders(): HttpHeaders {
    const token = sessionStorage.getItem('token');
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json',
    });
  }

  getAllGiras(): Observable<any[]> {
    const headers = this.getAuthHeaders();
    return this.http.get<any[]>(this.apiUrl, { headers });
  }

  createGira(gira: any): Observable<any> {
    const headers = this.getAuthHeaders();
    const body = {
      nombre: gira.nombre,
      descripcion: gira.descripcion,
      artistaId: gira.artistaId
    };
    return this.http.post<any>(this.apiUrl, body, { headers });
  }

  deleteGira(id: number): Observable<void> {
    const headers = this.getAuthHeaders();
    return this.http.delete<void>(`${this.apiUrl}/${id}`, { headers });
  }

  updateGira(id: number, gira: any): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.put<any>(`${this.apiUrl}/${id}`, gira, { headers });
  }
}
