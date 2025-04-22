import { Component, OnInit } from '@angular/core';
import { RecintoService } from '../recinto.service'; 
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CrearRecintoComponent } from '../crear-recinto/crear-recinto.component';
import { EditarRecintoComponent } from '../editar-recinto/editar-recinto.component';

@Component({
  selector: 'app-gestionar-recintos',
  standalone: true,
  imports: [CommonModule, FormsModule, CrearRecintoComponent, EditarRecintoComponent],
  templateUrl: './gestionar-recinto.component.html',
  styleUrls: ['./gestionar-recinto.component.css']
})
export class GestionarRecintosComponent implements OnInit {
  recintos: any[] = [];
  recintoSeleccionado: any = null;

  constructor(private recintoService: RecintoService) {}

  ngOnInit(): void {
    this.cargarRecintos();
  }

  cargarRecintos(): void {
    this.recintoService.obtenerRecintos().subscribe(data => {
      this.recintos = data;
    });
  }

  seleccionarRecinto(recinto: any): void {
    // Clonamos el recinto para evitar modificar directamente la lista
    this.recintoSeleccionado = { ...recinto };
  }

  onRecintoCreado(): void {
    this.cargarRecintos();
  }

  onRecintoActualizado(): void {
    this.cargarRecintos();
    this.recintoSeleccionado = null;
  }

  cancelarEdicion(): void {
    this.recintoSeleccionado = null;
  }

  eliminarRecinto(id: number): void {
    if (confirm('¿Estás seguro de eliminar este recinto?')) {
      this.recintoService.eliminarRecinto(id).subscribe(() => {
        this.cargarRecintos();
      });
    }
  }
}
