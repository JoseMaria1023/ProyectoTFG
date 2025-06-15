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
  pagina = 1;
  totalPaginas = 1;
  porPagina = 5;
  editando = false;
  mensaje = '';
  mostrarTransferencia = false;
  entradaSeleccionada: any = null;

  mostrarQrModal = false;
  qrSeleccionado: string | null = null;

  constructor(
    private authService: AuthService,
    private usuarioService: UsuarioService,
    private entradaService: EntradaService
  ) {}

  ngOnInit(): void {
    const id = this.authService.getUserId();
    if (id) {
      this.usuarioService.getUsuarioById(id).subscribe(data => {
        this.usuario = data;
      });
      this.cargarEntradas(id);
    }
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
    const id = this.authService.getUserId();
    if (!id) return;
    this.usuarioService.actualizarUsuario(id, this.usuario).subscribe({
      next: () => {
        this.mensaje = 'Datos actualizados.';
        this.editando = false;
      },
      error: () => {
        this.mensaje = 'Error al guardar.';
      }
    });
  }

  eliminarCuenta(event: any) {
    const check = event.target.checked;
    if (!check) {
      if (confirm('¿Eliminar cuenta permanentemente?')) {
        const id = this.authService.getUserId();
        if (!id) return;
        this.usuarioService.eliminarUsuario(id).subscribe({
          next: () => {
            alert('Cuenta eliminada.');
            this.authService.logout();
            location.href = '/home';
          },
          error: () => {
            alert('Error al eliminar.');
            event.target.checked = true;
          }
        });
      } else {
        event.target.checked = true;
      }
    } else {
      this.usuario.activo = true;
    }
  }

  desactivarTemporal() {
    if (confirm('¿Desactivar tu cuenta temporalmente?')) {
      const id = this.authService.getUserId();
      if (!id) return;
      this.usuarioService.actualizarUsuario(id, { ...this.usuario, activo: false }).subscribe({
        next: () => {
          this.usuario.activo = false;
          this.mensaje = 'Cuenta desactivada temporalmente.';
        },
        error: () => {
          alert('Error al desactivar.');
        }
      });
    }
  }

  abrirTransferencia(entrada: any) {
    sessionStorage.setItem('entradaSeleccionada', JSON.stringify(entrada));
    this.mostrarTransferencia = true;
  }

  cerrarTransferenciaYRefrescar() {
    this.mostrarTransferencia = false;
    this.entradaSeleccionada = null;
    const id = this.authService.getUserId();
    if (id) this.cargarEntradas(id);
  }

revenderEntrada(entrada: any) {
  const { precioReventaTemp: precio, precioVenta, idEntrada } = entrada;
  const max = precioVenta * 1.1;

  if (!precio || precio <= 0) return alert('Precio no válido.');
  if (precio > max) return alert(`Máximo permitido: €${max.toFixed(2)}`);

  this.entradaService.revenderEntrada(idEntrada, precio).subscribe({
    next: () => {
      alert('Entrada puesta en reventa.');
      const id = this.authService.getUserId();
      if (id) this.cargarEntradas(id);
    },
    error: () => {
      alert('Error al poner en reventa.');
    }
  });
}


  cambiarPagina(dir: string) {
    const total = Math.ceil(this.entradas.length / this.porPagina);
    if (dir === 'anterior' && this.pagina > 1) this.pagina--;
    if (dir === 'siguiente' && this.pagina < total) this.pagina++;
    this.actualizarEntradasPagina();
  }

  verQr(entrada: any) {
    this.qrSeleccionado = `data:image/png;base64,${entrada.codigoQR}`;
    this.mostrarQrModal = true;
  }

  cerrarQrModal() {
    this.qrSeleccionado = null;
    this.mostrarQrModal = false;
  }

  cargarEntradas(id: any) {
    this.usuarioService.getEntradasByUsuario(id).subscribe(lista => {
      this.entradas = lista.filter(entrada => entrada.estado !== 'TRANSFERIDA' && entrada.estado !== 'VENDIDA');
      this.entradas.forEach(entrada => entrada.precioReventaTemp = null);
      this.totalPaginas = Math.ceil(this.entradas.length / this.porPagina);
      this.pagina = 1;
      this.actualizarEntradasPagina();
    });
  }

  actualizarEntradasPagina() {
    const inicio = (this.pagina - 1) * this.porPagina;
    this.entradasPagina = this.entradas.slice(inicio, inicio + this.porPagina);
  }
}