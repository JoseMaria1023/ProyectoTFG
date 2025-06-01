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
  const id = sessionStorage.getItem('recintoSeleccionadoId');
  if (id) {
    this.recintoService.TraerRecintos().subscribe((data: any[]) => {
      const encontrado = data.find(recinto => recinto.idRecinto === +id);
      if (encontrado) {
        this.recinto = encontrado;
      } else {
        this.error = 'Recinto no encontrado en la lista.';
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
