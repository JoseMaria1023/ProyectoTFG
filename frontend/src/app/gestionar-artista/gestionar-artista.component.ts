import { Component, OnInit } from '@angular/core';
import { ArtistaService } from '../artista.service'; 
import { CommonModule } from '@angular/common';
import { CrearArtistaComponent } from '../crear-artista/crear-artista.component';
import { EditarArtistaComponent } from '../editar-artista/editar-artista.component';


@Component({
  selector: 'app-gestionar-artista',
  standalone: true,
  imports: [CommonModule, CrearArtistaComponent, EditarArtistaComponent],
  templateUrl: './gestionar-artista.component.html'
})
export class GestionarArtistaComponent implements OnInit {
  artistas: any[] = [];
  artistaSeleccionado: any = null;

  constructor(private artistaService: ArtistaService) {}

  ngOnInit(): void {
    this.cargarArtistas();
  }

  cargarArtistas(): void {
    this.artistaService.getArtistas().subscribe((data) => {
      this.artistas = data;
    });
  }

  seleccionarArtista(artista: any) {
    this.artistaSeleccionado = { ...artista };
  }

  cancelarEdicion() {
    this.artistaSeleccionado = null;
  }

  onArtistaCreado(): void {
    this.cargarArtistas();
  }

  onArtistaActualizado(): void {
    this.cargarArtistas();
    this.artistaSeleccionado = null;
  }

  eliminarArtista(id: number): void {
    if (confirm('¿Estás seguro de que quieres eliminar este artista?')) {
      this.artistaService.eliminarArtista(id).subscribe(() => {
        this.cargarArtistas();
      });
    }
  }
}
