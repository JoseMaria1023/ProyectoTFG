import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { GiraService } from '../gira.service'; 

@Component({
  selector: 'app-editar-gira',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './editar-gira.component.html',
  styleUrls: ['./editar-gira.component.css']
})
export class EditarGiraComponent {
  @Input() gira: any = { idGira: 0, nombre: '', descripcion: '', artistaId: null };
  @Input() artistas: any[] = [];
  @Output() giraActualizada = new EventEmitter<void>();
  @Output() cancelarEdicion = new EventEmitter<void>();

  constructor(private giraService: GiraService) {}

  actualizarGira(): void {
    this.giraService.updateGira(this.gira.idGira, this.gira).subscribe(
      (response) => {
        console.log('Gira actualizada:', response);
        alert('Gira actualizada con éxito');
        this.giraActualizada.emit();
      },
      (error) => {
        console.error('Error al actualizar gira:', error);
        alert('Error al actualizar la gira');
      }
    );
  }

  cancelar(): void {
    this.cancelarEdicion.emit();
  }
}
