import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-mapa-asientos',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './mapa-asientos.component.html',
  styleUrls: ['./mapa-asientos.component.css']
})
export class MapaAsientosComponent {
  @Input() asientos: any[] = [];
  @Input() concierto!: any;
  @Input() modo: 'cliente' | 'artista' = 'cliente';
  @Output() asientoSeleccionado = new EventEmitter<any>();
  @Output() cerrarModal = new EventEmitter<void>();

  get filas(): any[][] {
    const maxFila = Math.max(...this.asientos.map(a => a.fila), 0);
    return Array.from({ length: maxFila }, (_, i) =>
      this.asientos.filter(a => a.fila === i + 1)
    );
  }

  onClick(asiento: any): void {
    if (asiento.ocupado) return;

    if (this.modo === 'artista') {
      asiento.tipo = 'VIP';
      console.log('Asiento marcado VIP:', asiento);
    }

    this.asientoSeleccionado.emit(asiento);

    if (this.modo === 'cliente') {
      this.cerrarModal.emit();
    }
  }

  cerrar(): void {
    this.cerrarModal.emit();
  }
}