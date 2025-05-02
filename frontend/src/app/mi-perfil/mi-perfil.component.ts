import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../auth.service';
import { UsuarioService } from '../usuario.service';
import { EntradaService } from '../entrada.service';
import { TransferenciaEntradaComponent } from '../transferencia-entrada/transferencia-entrada.component';

@Component({
  selector: 'app-mi-perfil',
  standalone: true,
  imports: [CommonModule, FormsModule, TransferenciaEntradaComponent],
  templateUrl: './mi-perfil.component.html',
  styleUrls: ['./mi-perfil.component.css']
})
export class MiPerfilComponent implements OnInit {
  usuario: any = {};
  entradas: any[] = [];
  entradasPagina: any[] = [];
  entradaSeleccionada: any = null;

  editando = false;
  mostrarTransferencia = false;
  mensaje = '';
  paginaActual = 1;
  totalPaginas = 1;
  readonly entradasPorPagina = 5;

  // Para el modal de QR
  showQrModal = false;
  qrSeleccionado: string | null = null;

  constructor(
    private authService: AuthService,
    private usuarioService: UsuarioService,
    private entradaService: EntradaService
  ) {}

  ngOnInit(): void {
    const userId = this.authService.getUserId();
    if (!userId) return;

    this.usuarioService.getUsuarioById(userId)
      .subscribe(u => this.usuario = u);

    this.cargarEntradas(userId);
  }

  activarEdicion() {
    this.editando = true;
    this.mensaje = '';
  }

  cancelarEdicion() {
    this.editando = false;
    this.ngOnInit();
  }

  guardarCambios() {
    const userId = this.authService.getUserId();
    if (!userId) return;
    this.usuarioService.actualizarUsuario(userId, this.usuario)
      .subscribe({
        next: () => {
          this.mensaje = 'Cambios guardados con éxito.';
          this.editando = false;
        },
        error: () => this.mensaje = 'Error al guardar los cambios.'
      });
  }

  verificarActivo(event: Event) {
    const checkbox = (event.target as HTMLInputElement);
    if (!checkbox.checked) {
      if (confirm('¿Deseas eliminar tu cuenta permanentemente?')) {
        const userId = this.authService.getUserId();
        if (!userId) return;
        this.usuarioService.eliminarUsuario(userId).subscribe({
          next: () => {
            alert('Cuenta eliminada.');
            this.authService.logout();
            location.href = '/';
          },
          error: () => {
            alert('Error al eliminar la cuenta.');
            checkbox.checked = true;
          }
        });
      } else {
        checkbox.checked = true;
      }
    } else {
      this.usuario.activo = true;
    }
  }

  abrirTransferencia(entrada: any) {
    this.entradaSeleccionada = entrada;
    this.mostrarTransferencia = true;
  }

  cerrarTransferenciaYRefrescar() {
    this.mostrarTransferencia = false;
    this.entradaSeleccionada = null;
    this.cargarEntradas(this.authService.getUserId());
  }

  onRevender(entrada: any) {
    const precio = entrada.precioReventaTemp;
    const max = entrada.precioVenta * 1.1;
    if (!precio || precio <= 0) return alert('Precio inválido.');
    if (precio > max) return alert(`Máximo permitido: €${max.toFixed(2)}.`);
    this.entradaService.revenderEntrada(entrada.idEntrada, precio).subscribe({
      next: () => {
        alert('Entrada puesta en reventa.');
        this.cargarEntradas(this.authService.getUserId());
      },
      error: err => alert('Error al revender: ' + (err.error?.message || err.message))
    });
  }

  cambiarPagina(direccion: 'anterior'|'siguiente') {
    const total = Math.ceil(this.entradas.length / this.entradasPorPagina);
    if (direccion === 'anterior' && this.paginaActual > 1) this.paginaActual--;
    if (direccion === 'siguiente' && this.paginaActual < total) this.paginaActual++;
    this.actualizarEntradasPagina();
  }

  verQr(entrada: any) {
    this.qrSeleccionado = `data:image/png;base64,${entrada.codigoQR}`;
    this.showQrModal = true;
  }

  cerrarQrModal() {
    this.showQrModal = false;
    this.qrSeleccionado = null;
  }

  private cargarEntradas(userId: any) {
    this.usuarioService.getEntradasByUsuario(userId).subscribe(list => {
      this.entradas = list.filter(e => !['TRANSFERIDA','VENDIDA'].includes(e.estado));
      this.entradas.forEach(e => e.precioReventaTemp = null);
      this.paginaActual = 1;
      this.totalPaginas = Math.ceil(this.entradas.length / this.entradasPorPagina);
      this.actualizarEntradasPagina();
    });
  }

  private actualizarEntradasPagina() {
    const start = (this.paginaActual - 1) * this.entradasPorPagina;
    this.entradasPagina = this.entradas.slice(start, start + this.entradasPorPagina);
  }
}
