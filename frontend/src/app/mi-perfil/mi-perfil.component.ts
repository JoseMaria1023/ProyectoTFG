import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms'; 
import { AuthService } from '../auth.service';
import { UsuarioService } from '../usuario.service';
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

  editando = false;
  mensaje = '';

  // Para transferencia
  entradaSeleccionada: any = null;
  mostrarTransferencia = false;

  // Paginación
  paginaActual = 1;
  entradasPorPagina = 5;
  totalPaginas = 1;

  constructor(
    private authService: AuthService,
    private usuarioService: UsuarioService
  ) {}

  ngOnInit(): void {
    const userId = this.authService.getUserId();
    if (!userId) return;

    this.usuarioService.getUsuarioById(userId).subscribe(u => this.usuario = u);
    this.usuarioService.getEntradasByUsuario(userId).subscribe(list => {
      this.entradas = list;
      this.totalPaginas = Math.ceil(this.entradas.length / this.entradasPorPagina);
      this.actualizarEntradasPagina();
    });
  }

  activarEdicion() {
    this.editando = true;
    this.mensaje = '';
  }

  guardarCambios() {
    const userId = this.authService.getUserId();
    if (!userId) return;
    this.usuarioService.actualizarUsuario(userId, this.usuario).subscribe({
      next: () => {
        this.mensaje = 'Cambios guardados con éxito.';
        this.editando = false;
      },
      error: () => this.mensaje = 'Hubo un error al guardar los cambios.'
    });
  }

  verificarActivo(event: Event): void {
    const checkbox = event.target as HTMLInputElement;
    if (!checkbox.checked) {
      const confirmacion = confirm('Si desactivas tu cuenta, se eliminará permanentemente. ¿Estás seguro?');
      if (confirmacion) {
        const userId = this.authService.getUserId();
        if (userId) {
          this.usuarioService.eliminarUsuario(userId).subscribe({
            next: () => {
              alert('Tu cuenta ha sido eliminada.');
              this.authService.logout(); 
              location.href = '/';
            },
            error: () => {
              alert('Hubo un error al eliminar la cuenta.');
              checkbox.checked = true;
            }
          });
        }
      } else {
        checkbox.checked = true;
      }
    } else {
      this.usuario.activo = true;
    }
  }

  cancelarEdicion() {
    this.editando = false;
    this.ngOnInit();
  }

  abrirTransferencia(e: any) {
    this.entradaSeleccionada = e;
    this.mostrarTransferencia = true;
  }

  cerrarTransferencia() {
    this.mostrarTransferencia = false;
    this.entradaSeleccionada = null;
  }

  // Paginación
  cambiarPagina(dir: 'anterior' | 'siguiente') {
    if (dir === 'anterior' && this.paginaActual > 1) {
      this.paginaActual--;
    } else if (dir === 'siguiente' && this.paginaActual < this.totalPaginas) {
      this.paginaActual++;
    }
    this.actualizarEntradasPagina();
  }

  actualizarEntradasPagina() {
    const start = (this.paginaActual - 1) * this.entradasPorPagina;
    this.entradasPagina = this.entradas.slice(start, start + this.entradasPorPagina);
  }
}

