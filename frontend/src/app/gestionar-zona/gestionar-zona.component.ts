import { Component, OnInit } from '@angular/core';
import { ZonaService } from '../zona.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
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
  // Si se selecciona una zona para editar, se guarda aquí
  zonaSeleccionada: any = null;
  // Se asume que si es necesario se puede cargar también una lista de recintos para el select; de lo contrario se delega a otro componente o se ignora.
  recintos: any[] = [];

  constructor(private zonaService: ZonaService) {}

  ngOnInit(): void {
    this.cargarZonas();
  }

  cargarZonas(): void {
    this.zonaService.obtenerZonas().subscribe(data => {
      this.zonas = data;
    });
  }

  seleccionarZona(zona: any): void {
    this.zonaSeleccionada = { ...zona };
  }

  onZonaCreada(): void {
    this.cargarZonas();
  }

  onZonaActualizada(): void {
    this.cargarZonas();
    this.zonaSeleccionada = null;
  }

  cancelarEdicion(): void {
    this.zonaSeleccionada = null;
  }

  eliminarZona(id: number): void {
    if (confirm('¿Estás seguro de eliminar esta zona?')) {
      this.zonaService.eliminarZona(id).subscribe(() => {
        this.cargarZonas();
      });
    }
  }
}
