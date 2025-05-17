import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AsientoService } from '../asiento.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-editar-asiento',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './editar-asiento.component.html',
  styleUrls: ['./editar-asiento.component.css']
})
export class EditarAsientoComponent implements OnInit {
  asiento: any = {
    idAsiento: 0,
    numeracion: '',
    fila: 0,
    columna: 0,
    tipo: 'Normal',
    conciertoId: null
  };

  constructor(
    private route: ActivatedRoute,
    private asientoService: AsientoService
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.asientoService.getAsientoPorId(+id).subscribe(
        (data) => {
          this.asiento = data;
        },
        (error) => {
          console.error('Error al cargar asiento:', error);
          alert('Error al cargar el asiento');
        }
      );
    }
  }

  actualizarAsiento(): void {
    this.asientoService.actualizarAsiento(this.asiento.idAsiento, this.asiento).subscribe(
      response => {
        console.log('Asiento actualizado:', response);
        alert('Asiento actualizado con éxito');
      },
      error => {
        console.error('Error al actualizar asiento:', error);
        alert('Error al actualizar el asiento');
      }
    );
  }

  eliminarAsiento(): void {
    if (confirm('¿Estás seguro de eliminar este asiento?')) {
      this.asientoService.eliminarAsiento(this.asiento.idAsiento).subscribe(
        response => {
          console.log('Asiento eliminado:', response);
          alert('Asiento eliminado con éxito');
        },
        error => {
          console.error('Error al eliminar asiento:', error);
          alert('Error al eliminar el asiento');
        }
      );
    }
  }
}
