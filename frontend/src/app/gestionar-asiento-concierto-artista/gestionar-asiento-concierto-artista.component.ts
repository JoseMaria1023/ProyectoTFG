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

    this.conciertoService.TraerConciertosPorArtista(artistaId)
      .subscribe(data => {
        this.conciertos = data.filter(c => !this.esConciertoPasado(c));
      }, console.error);
  }

  esConciertoPasado(concierto: any): boolean {
    const hoy = new Date();
    hoy.setHours(0, 0, 0, 0);

    const fechaConcierto = new Date(concierto.fecha);
    fechaConcierto.setHours(0, 0, 0, 0);

    return fechaConcierto < hoy;
  }

  isArtista(): boolean {
    return this.authService.getUserRole() === 'ROLE_ARTISTA';
  }

  gestionarAsientos(concierto: any): void {
    this.conciertoSeleccionado = concierto;
    this.mostrarModal = true;
    this.conciertoService.TraerAsientosPorConcierto(concierto.idConcierto)
      .subscribe(data => this.asientos = data, console.error);
  }

  onAsientoSeleccionado(asiento: any): void {
    if (this.isArtista()) {
      this.asientoService.actualizarAsiento(asiento.idAsiento, asiento)
        .subscribe(updated => {
          this.asientos = this.asientos.map(a =>
            a.idAsiento === updated.idAsiento ? updated : a
          );
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
