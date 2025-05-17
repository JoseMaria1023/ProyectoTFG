import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ArtistaService {
  private apiUrl = 'http://localhost:9000/api/artistas';

  constructor(private http: HttpClient) {}

  private getAuthHeaders(isJson: boolean = true): HttpHeaders {
    const token = sessionStorage.getItem('token');
    let headersConfig: any = {
      'Authorization': `Bearer ${token}`
    };
    if (isJson) {
      headersConfig['Content-Type'] = 'application/json';
    }
    return new HttpHeaders(headersConfig);
  }

  registrarArtista(artista: FormData): Observable<any> {
    const headers = this.getAuthHeaders(false);
    return this.http.post( `${this.apiUrl}/crear`,artista,{ headers });
  }

  getArtistas(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/listar`);
  }

   actualizarArtista(id: number, artista: FormData): Observable<any> {
    const token = sessionStorage.getItem('token');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.put(`${this.apiUrl}/actualizar/${id}`, artista, { headers });
  }
  
  eliminarArtista(id: number): Observable<void> {
    const headers = this.getAuthHeaders();
    return this.http.delete<void>(`${this.apiUrl}/eliminar/${id}`, { headers });
  }
   getArtistaPorId(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }
}
