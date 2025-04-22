// entrada.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EntradaService {
  private apiUrl = 'http://localhost:9000/api/entradas';

  constructor(private http: HttpClient) {}

  crearEntrada(entrada: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, entrada);
  }

  obtenerEntradas(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/TodasEntradas`);
  }

  obtenerEntradaPorId(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }
}
