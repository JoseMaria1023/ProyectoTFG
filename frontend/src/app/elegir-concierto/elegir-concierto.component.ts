// elegir-concierto.component.ts
import { Component, OnInit } from '@angular/core';
import { ConciertoService } from '../concierto.service';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ArtistaService } from '../artista.service';

@Component({
  selector: 'app-elegir-concierto',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './elegir-concierto.component.html',
  styleUrls: ['./elegir-concierto.component.css']
})
export class ElegirConciertoComponent implements OnInit {
  conciertos: any[] = [];
  artistas: any[] = [];
  artistaSeleccionado: any;

  constructor(
    private conciertoService: ConciertoService,
    private artistaService: ArtistaService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.cargarTodos();
    this.obtenerArtistas();
  }

  cargarTodos(): void {
    this.conciertoService.obtenerConciertos().subscribe(
      data => this.conciertos = data,
      error => console.error('Error al obtener conciertos:', error)
    );
  }

  obtenerArtistas(): void {
    this.artistaService.getArtistas().subscribe(
      data => this.artistas = data,
      error => console.error('Error al obtener artistas:', error)
    );
  }

  filtrarPorArtista(): void {
    if (!this.artistaSeleccionado) return;

    this.conciertoService.obtenerConciertosPorArtista(this.artistaSeleccionado.idArtista).subscribe(
      data => this.conciertos = data,
      error => console.error('Error al filtrar conciertos por artista:', error)
    );
  }

  comprarEntrada(concierto: any): void {
    this.router.navigate(['/Comprar-entrada'], { 
      queryParams: { 
        conciertoId: concierto.idConcierto, 
        conciertoNombre: concierto.nombre 
      } 
    });
  }
}