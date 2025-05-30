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
  fechaDesde: string = '';
  fechaHasta: string = '';
  tipoFiltro: string = ''; 
  estadoSeleccionado: string = ''; 

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
      err  => console.error(err)
    );
  }

  filtrarPorArtista(): void {
    if (!this.artistaSeleccionado) return;
    this.conciertoService.obtenerConciertosPorArtista(this.artistaSeleccionado.idArtista)
      .subscribe(data => this.conciertos = data);
  }

  comprarEntrada(concierto: any): void {
    this.router.navigate(['/Comprar-entrada'], {
      queryParams: {
        conciertoId: concierto.idConcierto,
        conciertoNombre: concierto.nombre
      }
    });
  }

  aplicarFiltro() {
    const filtros: any = {};
    if (this.tipoFiltro === 'artista' && this.artistaSeleccionado) {
      filtros.artistaId = this.artistaSeleccionado.idArtista;
    } else if (this.tipoFiltro === 'fecha') {
      filtros.fechaDesde = this.fechaDesde;
      filtros.fechaHasta = this.fechaHasta;
    } else if (this.tipoFiltro === 'estado') {
      filtros.estado = this.estadoSeleccionado;
    }

    this.conciertoService.filtrarConciertos(filtros).subscribe(data => {
      this.conciertos = data;
    });
  }

  obtenerArtistas(): void {
    this.artistaService.getArtistas().subscribe(
      data => this.artistas = data,
      err  => console.error(err)
    );
  }
}
