import { Component, OnInit }    from '@angular/core';
import { FormsModule }          from '@angular/forms';
import { CommonModule }         from '@angular/common';
import { ActivatedRoute }       from '@angular/router';

import { AsientoService }       from '../asiento.service'; 
import { ConciertoService }     from '../concierto.service';

@Component({
  selector: 'app-crear-asiento',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './crear-asiento.component.html',
  styleUrls: ['./crear-asiento.component.css']
})
export class CrearAsientoComponent implements OnInit {
  numFilas = 5;
  numColumnas = 5;
  selectedConciertoId: number | null = null;
  conciertos: any[] = [];

  constructor(
    private asientoService: AsientoService,
    private conciertoService: ConciertoService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    // 1) Leer conciertoId del query param (si viene)
    const qp = this.route.snapshot.queryParamMap.get('conciertoId');
    if (qp) {
      this.selectedConciertoId = +qp;
    }

    // 2) Cargar lista de conciertos
    this.conciertoService.obtenerConciertos().subscribe({
      next: data => {
        this.conciertos = data;
        // Si no venía en queryParams, ponemos el primero
        if (!this.selectedConciertoId && this.conciertos.length) {
          this.selectedConciertoId = this.conciertos[0].idConcierto;
        }
      },
      error: err => console.error('Error al obtener conciertos', err)
    });
  }

  configurarAsientos(): void {
    if (!this.selectedConciertoId) {
      return alert('Por favor, selecciona un concierto.');
    }

    for (let fila = 1; fila <= this.numFilas; fila++) {
      for (let col = 1; col <= this.numColumnas; col++) {
        const nuevoAsiento = {
          numeracion: `F${fila}-C${col}`,
          fila,
          columna: col,
          tipo: 'Normal',
          conciertoId: this.selectedConciertoId
        };

        this.asientoService.crearAsiento(nuevoAsiento).subscribe({
          next: resp => console.log('Asiento creado:', resp),
          error: err => console.error('Error al crear asiento:', err)
        });
      }
    }

    alert(
      `Se han configurado ${this.numFilas * this.numColumnas} asientos para el concierto ${this.selectedConciertoId}`
    );
  }
}
