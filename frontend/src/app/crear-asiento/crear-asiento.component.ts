import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AsientoService } from '../asiento.service'; 
import { ConciertoService } from '../concierto.service';
@Component({
  selector: 'app-crear-asiento',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './crear-asiento.component.html',
  styleUrls: ['./crear-asiento.component.css']
})
export class CrearAsientoComponent {
  numFilas: number = 5;
  numColumnas: number = 5;
  selectedConciertoId: number | null = null;
  conciertos: any[] = [];
  

  constructor(
    private asientoService: AsientoService,
    private conciertoService: ConciertoService
  ) {}

  ngOnInit(): void {
    // Cargar conciertos disponibles
    this.conciertoService.obtenerConciertos().subscribe(
      (data) => {
        this.conciertos = data;
      },
      (error) => {
        console.error('Error al obtener conciertos', error);
      }
    );
  }

  configurarAsientos(): void {
    if (!this.selectedConciertoId) {
      alert('Por favor, selecciona un concierto.');
      return;
    }

    // Iterar por cada fila y columna para crear cada asiento
    for (let fila = 1; fila <= this.numFilas; fila++) {
      for (let col = 1; col <= this.numColumnas; col++) {
        const numeracion = `F${fila}-C${col}`;
        const nuevoAsiento = {
          numeracion: numeracion,
          fila: fila,
          columna: col,
          tipo: 'Normal',   // Puedes ampliar esta lógica si hay diferentes tipos
          conciertoId: this.selectedConciertoId
        };

        this.asientoService.crearAsiento(nuevoAsiento).subscribe(
          (response) => {
            console.log('Asiento creado:', response);
          },
          (error) => {
            console.error('Error al crear asiento:', error);
          }
        );
      }
    }
    alert(`Se han configurado ${this.numFilas * this.numColumnas} asientos para el concierto ${this.selectedConciertoId}`);
  }
}
