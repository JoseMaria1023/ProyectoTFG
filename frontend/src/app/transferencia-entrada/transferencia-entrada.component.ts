import { Component } from '@angular/core';
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
  entrada: any = null;
  telefonoDestino: string = '';
  mensaje: string = '';
  error: string = '';

  constructor(
    private transferenciaService: TransferenciaService,
    private authService: AuthService
  ) {
    const entradaGuardada = sessionStorage.getItem('entradaSeleccionada');
    if (entradaGuardada) {
      this.entrada = JSON.parse(entradaGuardada);
    }
  }

  transferirEntrada(): void {
    this.mensaje = '';
    this.error = '';

    if (!this.telefonoDestino.trim()) {
      this.error = 'Debes introducir un número de teléfono válido.';
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
        setTimeout(() => this.cerrarModal(), 1500);
      },
      error: err => {
        this.error = 'Error al realizar la transferencia: ' + (err.error?.message || 'Intenta de nuevo.');
      }
    });
  }

  cerrarModal(): void {
    sessionStorage.removeItem('entradaSeleccionada');
    window.location.reload(); // o emitir evento global si prefieres evitar recarga
  }
}
