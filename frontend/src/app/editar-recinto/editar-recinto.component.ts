import { Component, Input, Output, EventEmitter } from '@angular/core';
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
export class EditarRecintoComponent {
  // Se espera que el recinto sea un objeto con idRecinto, nombre, ubicacion y capacidadTotal
  @Input() recinto: any = { idRecinto: 0, nombre: '', ubicacion: '', capacidadTotal: 0 };
  @Output() recintoActualizado = new EventEmitter<void>();
  @Output() cancelarEdicion = new EventEmitter<void>();

  constructor(private recintoService: RecintoService) {}

  actualizarRecinto(): void {
    this.recintoService.actualizarRecinto(this.recinto.idRecinto, this.recinto).subscribe(
      response => {
        console.log('Recinto actualizado:', response);
        alert('Recinto actualizado con éxito');
        this.recintoActualizado.emit();
      },
      error => {
        console.error('Error al actualizar recinto:', error);
        alert('Error al actualizar el recinto');
      }
    );
  }

  cancelar(): void {
    this.cancelarEdicion.emit();
  }
}
