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
  @Input() asientos: Array<{ fila: number; columna: number; ocupado: boolean; tipo?: string }> = [];
  @Input() concierto!: { nombre: string };
  @Input() modo: 'user' | 'artista' = 'user';

  @Output() asientoSeleccionado = new EventEmitter<any>();
  @Output() cerrarModal       = new EventEmitter<void>();

  // Constantes de diseño (públicas para la plantilla)
  public readonly rowH = 60;
  public readonly hdrH = 120;
  public readonly maxRows = 6;

  // Agrupa asientos por fila
  get filas(): any[][] {
    const maxFila = this.asientos.reduce((m, a) => Math.max(m, a.fila), 0);
    return Array.from({ length: maxFila }, (_, i) =>
      this.asientos.filter(a => a.fila === i + 1)
    );
  }

  // Calcula altura del modal según número de filas
  get modalHeight(): number {
    const visibles = Math.min(this.filas.length, this.maxRows);
    return this.hdrH + visibles * this.rowH;
  }

  onClick(as: any) {
    if (as.ocupado) return;
    if (this.modo === 'artista') as.tipo = 'VIP';
    this.asientoSeleccionado.emit(as);
    if (this.modo === 'user') this.cerrarModal.emit();
  }

  cerrar() {
    this.cerrarModal.emit();
  }
}
