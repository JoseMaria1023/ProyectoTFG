import { Component, OnInit } from '@angular/core';
import { ConciertoService } from '../concierto.service'; 

@Component({
  selector: 'app-filtrar-conciertos',
  templateUrl: './filtrar-conciertos.component.html',
  styleUrls: ['./filtrar-conciertos.component.css']
})
export class FiltrarConciertosComponent implements OnInit {
  artistaId: number = 0;
  conciertos: any[] = [];
  error: string = '';

  constructor(private conciertoService: ConciertoService) {}

  ngOnInit(): void {}

  buscarConciertosPorArtista(): void {
    if (!this.artistaId) {
      this.error = 'Por favor, introduce un ID de artista válido';
      this.conciertos = [];
      return;
    }

    this.conciertoService.obtenerConciertosPorArtista(this.artistaId).subscribe({
      next: data => {
        this.conciertos = data;
        this.error = '';
      },
      error: err => {
        this.error = 'No se pudieron obtener los conciertos para el artista especificado.';
        this.conciertos = [];
      }
    });
  }
}