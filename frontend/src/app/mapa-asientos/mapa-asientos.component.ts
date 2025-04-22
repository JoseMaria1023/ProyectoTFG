import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';

export interface Asiento {
  idAsiento: number;
  fila: number;
  columna: number;
  ocupado: boolean;
  tipo?: 'NORMAL' | 'VIP'; // Nuevo campo tipo
}

@Component({
  selector: 'app-mapa-asientos',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './mapa-asientos.component.html',
  styleUrls: ['./mapa-asientos.component.css']
})
export class MapaAsientosComponent {
  @Input() asientos: Asiento[] = [];
  @Input() concierto: any;
  @Input() modo: 'cliente' | 'artista' = 'cliente';
  @Output() asientoSeleccionado = new EventEmitter<Asiento>();
  @Output() cerrarModal = new EventEmitter<void>();

  seleccionarCliente(asiento: Asiento): void {
    if (!asiento.ocupado) {
      this.asientoSeleccionado.emit(asiento);
      this.cerrarModal.emit();
    }
  }

  seleccionarArtista(asiento: Asiento): void {
    if (!asiento.ocupado) {
      asiento.tipo = 'VIP';  // Marcar como VIP
      this.asientoSeleccionado.emit(asiento);
      this.cerrarModal.emit();
    }
  }

  // Método para asignar el estilo dorado al asiento VIP en el modal
  getAsientoClase(asiento: Asiento): string {
    return asiento.tipo === 'VIP' ? 'vip' : '';
  }

  // Método para obtener el estilo del botón según el estado del asiento
  obtenerEstilo(asiento: Asiento): string {
    if (asiento.ocupado) {
      return '#e74c3c'; // Rojo para ocupado
    } else if (asiento.tipo === 'VIP') {
      return 'gold'; // Dorado para VIP
    } else {
      return '#fff'; // Blanco para normales
    }
  }

  cerrar(): void {
    this.cerrarModal.emit();
  }
}
