import { Component } from '@angular/core';
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
  nombre: string = '';
  ubicacion: string = '';
  capacidadTotal: number = 0;

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
        this.resetFormulario();
      },
      error => {
        console.error('Error al crear el recinto', error);
        alert('Error al crear el recinto');
      }
    );
  }

  private resetFormulario(): void {
    this.nombre = '';
    this.ubicacion = '';
    this.capacidadTotal = 0;
  }
}
