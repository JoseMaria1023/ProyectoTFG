import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class TransferenciaService {
  private apiUrl = 'http://localhost:9000/api/transferencias';

  constructor(private http: HttpClient) {}

  transferirEntrada(idEntrada: number, usuarioOrigenId: number, telefonoDestino: string) {
    return this.http.post(`${this.apiUrl}/transferir`, {idEntrada,usuarioOrigenId,telefonoDestino});
  }
}
