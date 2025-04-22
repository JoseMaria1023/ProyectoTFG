import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { TransferenciaService } from '../transferencia.service';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-transferencia-entrada',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './transferencia-entrada.component.html',
  styleUrls: ['./transferencia-entrada.component.css']
})
export class TransferenciaEntradaComponent {
  @Input() entrada: any;
  @Output() cerrar = new EventEmitter<void>();

  telefonoDestino: string = '';
  mensaje: string = '';
  error: string = '';

  constructor(
    private transferenciaService: TransferenciaService,
    private authService: AuthService
  ) {}

  transferirEntrada(): void {
    this.mensaje = '';
    this.error = '';
  
    if (!this.telefonoDestino.trim()) {
      this.error = 'Debes ingresar un número de teléfono válido.';
      return;
    }
  
    const usuarioOrigenId = this.authService.getUserId();
  
    if (usuarioOrigenId === null) {
      this.error = 'No se pudo identificar al usuario. Intenta iniciar sesión de nuevo.';
      return;
    }
  
    this.transferenciaService.transferirEntrada(
      this.entrada.idEntrada,
      usuarioOrigenId,
      this.telefonoDestino
    ).subscribe({
      next: () => {
        this.mensaje = 'Transferencia realizada con éxito.';
        this.telefonoDestino = '';
        this.error = '';
        setTimeout(() => this.cerrar.emit(), 1500);
      },
      error: err => {
        this.error = 'Error al realizar la transferencia: ' + (err.error?.message || 'Intenta de nuevo.');
        this.mensaje = '';
      }
    });
  }
  

  cerrarModal(): void {
    this.cerrar.emit();
  }
}
