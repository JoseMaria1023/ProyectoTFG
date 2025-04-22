import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ArtistaService {
  private apiUrl = 'http://localhost:9000/api/artistas';

  constructor(private http: HttpClient) {}

  registrarArtista(artista: FormData): Observable<any> {
    return this.http.post(`${this.apiUrl}/crear`, artista);
  }
  getArtistas(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/listar`);
  }
  actualizarArtista(id: number, artista: FormData): Observable<any> {
    return this.http.put(`${this.apiUrl}/actualizar/${id}`, artista);
  }
  
  eliminarArtista(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/eliminar/${id}`);
  }
  
  
}
