import { Component, OnInit } from '@angular/core';
import { GiraService } from '../gira.service'; 
import { ArtistaService } from '../artista.service'; 
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CrearGiraComponent } from '../crear-gira/crear-gira.component';
import { EditarGiraComponent } from '../editar-gira/editar-gira.component';

@Component({
  selector: 'app-gestionar-gira',
  standalone: true,
  imports: [CommonModule, FormsModule, CrearGiraComponent, EditarGiraComponent],
  templateUrl: './gestionar-gira.component.html',
  styleUrls: ['./gestionar-gira.component.css']
})
export class GestionarGiraComponent implements OnInit {
  giras: any[] = [];
  artistas: any[] = [];
  giraSeleccionada: any = null;

  constructor(
    private giraService: GiraService,
    private artistaService: ArtistaService
  ) {}

  ngOnInit(): void {
    this.cargarGiras();
    this.cargarArtistas();
  }

  cargarGiras(): void {
    this.giraService.getAllGiras().subscribe(data => {
      this.giras = data;
    });
  }

  cargarArtistas(): void {
    this.artistaService.getArtistas().subscribe(data => {
      this.artistas = data;
    });
  }

  seleccionarGira(gira: any): void {
    // Al editar, se clona la gira para evitar modificar la lista directamente
    this.giraSeleccionada = { ...gira };
  }

  onGiraCreada(): void {
    this.cargarGiras();
  }

  onGiraActualizada(): void {
    this.cargarGiras();
    this.giraSeleccionada = null;
  }

  cancelarEdicion(): void {
    this.giraSeleccionada = null;
  }

  eliminarGira(id: number): void {
    if (confirm('¿Estás seguro de eliminar esta gira?')) {
      this.giraService.deleteGira(id).subscribe(() => {
        this.cargarGiras();
      });
    }
  }
}
