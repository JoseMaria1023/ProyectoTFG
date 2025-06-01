// gestionar-zona.component.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ZonaService } from '../zona.service';
import { CrearZonaComponent } from '../crear-zona/crear-zona.component';
import { EditarZonaComponent } from '../editar-zona/editar-zona.component';

@Component({
  selector: 'app-gestionar-zona',
  standalone: true,
  imports: [CommonModule, FormsModule, CrearZonaComponent, EditarZonaComponent],
  templateUrl: './gestionar-zona.component.html',
  styleUrls: ['./gestionar-zona.component.css']
})
export class GestionarZonaComponent implements OnInit {
  zonas: any[] = [];
  editarVisible = false;

  constructor(private zonaService: ZonaService) {}

  ngOnInit(): void {
    this.cargarZonas();
  }

  cargarZonas(): void {
    this.zonaService.TraerZonas().subscribe(data => this.zonas = data);
  }

  seleccionarZona(idZona: number): void {
    sessionStorage.setItem('zonaSeleccionadaId', idZona.toString());
    this.editarVisible = true;
  }

  cancelarEdicion(): void {
    sessionStorage.removeItem('zonaSeleccionadaId');
    this.editarVisible = false;
    this.cargarZonas();
  }

  eliminarZona(id: number): void {
    if (!confirm('¿Estás seguro de eliminar esta zona?')) return;
    this.zonaService.eliminarZona(id).subscribe(() => this.cargarZonas());
  }
}
