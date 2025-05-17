import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RecintoService } from '../recinto.service';

@Component({
  selector: 'app-editar-recinto',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './editar-recinto.component.html',
  styleUrls: ['./editar-recinto.component.css']
})
export class EditarRecintoComponent implements OnInit {
  recinto: any = {
    idRecinto: 0,
    nombre: '',
    ubicacion: '',
    capacidadTotal: 0
  };
  mensaje = '';
  error = '';

  constructor(private recintoService: RecintoService) {}

  ngOnInit(): void {
    const idStr = sessionStorage.getItem('recintoSeleccionadoId');
    if (idStr) {
      const id = Number(idStr);
      this.recintoService.obtenerRecintoPorId(id).subscribe({
        next: data => this.recinto = data,
        error: err => {
          console.error('Error al cargar recinto:', err);
          this.error = 'No se pudo cargar el recinto para editar.';
        }
      });
    } else {
      this.error = 'No hay ningún recinto seleccionado.';
    }
  }

 actualizarRecinto(): void {
  this.mensaje = '';
  this.error = '';

  this.recintoService.actualizarRecinto(this.recinto.idRecinto, this.recinto)
    .subscribe({
      next: () => {
        this.mensaje = 'Recinto actualizado con éxito.';
        sessionStorage.removeItem('recintoSeleccionadoId');
        
        // Notifica al padre sin Output
        window.dispatchEvent(new Event('recintoActualizado'));
      },
      error: err => {
        console.error('Error al actualizar recinto:', err);
        this.error = 'Error al actualizar el recinto.';
      }
    });
}

  cancelar(): void {
    sessionStorage.removeItem('recintoSeleccionadoId');
    window.location.reload();
  }
}