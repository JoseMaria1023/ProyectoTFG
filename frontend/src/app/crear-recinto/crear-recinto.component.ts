import { Component, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RecintoService } from '../recinto.service'; 

@Component({
  selector: 'app-crear-recinto',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './crear-recinto.component.html',
  styleUrls: ['./crear-recinto.component.css']
})
export class CrearRecintoComponent {
  // Campos del formulario
  nombre: string = '';
  ubicacion: string = '';
  capacidadTotal: number = 0;

  // Evento para notificar al componente padre que se creó un recinto
  @Output() recintoCreado = new EventEmitter<void>();

  constructor(private recintoService: RecintoService) {}

  onSubmit() {
    const recinto = {
      nombre: this.nombre,
      ubicacion: this.ubicacion,
      capacidadTotal: this.capacidadTotal
    };

    this.recintoService.crearRecinto(recinto).subscribe(
      response => {
        console.log('Recinto creado con éxito', response);
        alert('Recinto creado con éxito');
        // Reiniciamos los campos del formulario
        this.nombre = '';
        this.ubicacion = '';
        this.capacidadTotal = 0;
        this.recintoCreado.emit();
      },
      error => {
        console.error('Error al crear el recinto', error);
        alert('Error al crear el recinto');
      }
    );
  }
}
