import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RecintoService } from '../recinto.service';
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
  editarVisible = false;

  constructor(private recintoService: RecintoService) {}

  ngOnInit(): void {
    this.cargarRecintos();

    window.addEventListener('recintoActualizado', () => {
    this.refrescarYCancelar();
  });
  }

  cargarRecintos(): void {
    this.recintoService.obtenerRecintos().subscribe(data => {
      this.recintos = data;
    });
  }

  seleccionarRecinto(idRecinto: number): void {
    sessionStorage.setItem('recintoSeleccionadoId', idRecinto.toString());
    this.editarVisible = true;
  }

  // Se llama desde EditarRecintoComponent tras guardar
  refrescarYCancelar(): void {
    sessionStorage.removeItem('recintoSeleccionadoId');
    this.editarVisible = false;
    this.cargarRecintos();
  }

  cancelarEdicion(): void {
    sessionStorage.removeItem('recintoSeleccionadoId');
    this.editarVisible = false;
  }

  eliminarRecinto(id: number): void {
    if (!confirm('¿Estás seguro de eliminar este recinto?')) {
      return;
    }
    this.recintoService.eliminarRecinto(id).subscribe(() => {
      this.cargarRecintos();
    });
  }
}
