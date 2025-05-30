import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MapaAsientosComponent } from '../mapa-asientos/mapa-asientos.component';
import { ConciertoService } from '../concierto.service';
import { AuthService } from '../auth.service';
import { AsientoService } from '../asiento.service';

@Component({
  selector: 'app-gestionar-asiento-concierto-artista',
  standalone: true,
  imports: [CommonModule, MapaAsientosComponent],
  templateUrl: './gestionar-asiento-concierto-artista.component.html',
  styleUrls: ['./gestionar-asiento-concierto-artista.component.css']
})
export class GestionarAsientoConciertoArtistaComponent implements OnInit {
  conciertos: any[] = [];
  mostrarModal = false;
  asientos: any[] = [];
  conciertoSeleccionado: any = null;

  constructor(
    private conciertoService: ConciertoService,
    private authService: AuthService,
    private asientoService: AsientoService
  ) {}

  ngOnInit(): void {
    const artistaId = this.authService.getUserId();
    if (!artistaId) return;
    this.conciertoService.obtenerConciertosPorArtista(artistaId)
      .subscribe(data => this.conciertos = data, console.error);
  }

  isArtista(): boolean {
    return this.authService.getUserRole() === 'ROLE_ARTISTA';
  }

  gestionarAsientos(concierto: any): void {
    this.conciertoSeleccionado = concierto;
    this.mostrarModal = true;
    this.conciertoService.obtenerAsientosPorConcierto(concierto.idConcierto)
      .subscribe(data => this.asientos = data, console.error);
  }

  onAsientoSeleccionado(asiento: any): void {
    if (this.isArtista()) {
      asiento.precioVenta = 43;

      this.asientoService.actualizarAsiento(asiento.idAsiento, asiento)
        .subscribe(updated => {
          console.log('Respuesta server:', updated);
          // Sustituimos el asiento en el array con el que viene del servidor
          this.asientos = this.asientos.map(a =>
            a.idAsiento === updated.idAsiento ? updated : a
          );
          // no cerramos el modal para que veas el VIP
        }, console.error);
    } else {
      console.log('Comprar asiento', asiento);
      this.cerrarModal();
    }
  }

  cerrarModal(): void {
    this.mostrarModal = false;
  }
}
