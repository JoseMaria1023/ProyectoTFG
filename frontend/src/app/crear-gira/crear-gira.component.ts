import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { GiraService } from '../gira.service'; 

@Component({
  selector: 'app-crear-gira',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './crear-gira.component.html',
  styleUrls: ['./crear-gira.component.css']
})
export class CrearGiraComponent {
  // Se recibe la lista de artistas para el select
  @Input() artistas: any[] = [];
  // Emitirá un evento para notificar que se creó una gira
  @Output() giraCreada = new EventEmitter<void>();

  // Objeto gira inicial
  gira: any = { nombre: '', descripcion: '', artistaId: null };

  constructor(private giraService: GiraService) {}

  crearGira(): void {
    // Se llama al servicio para crear la gira
    this.giraService.createGira(this.gira).subscribe(
      (response) => {
        console.log('Gira creada:', response);
        alert('Gira creada con éxito');
        this.resetForm();
        this.giraCreada.emit();
      },
      (error) => {
        console.error('Error al crear gira:', error);
        alert('Error al crear la gira');
      }
    );
  }

  resetForm(): void {
    this.gira = { nombre: '', descripcion: '', artistaId: null };
  }
}
