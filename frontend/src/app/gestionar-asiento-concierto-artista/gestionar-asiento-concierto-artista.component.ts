import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MapaAsientosComponent, Asiento } from '../mapa-asientos/mapa-asientos.component';
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
  mostrarModal: boolean = false;
  asientos: any[] = []; // Cambiar Asiento a any
  conciertoSeleccionado: any = null;
  
  // Constructor
  constructor(
    private conciertoService: ConciertoService,
    private authService: AuthService,
    private asientoService: AsientoService
  ) {}

  ngOnInit(): void {
    const artistaId = this.authService.getUserId();
    if (artistaId) {
      this.conciertoService.obtenerConciertosPorArtista(artistaId).subscribe(
        data => { this.conciertos = data; },
        error => { console.error('Error al obtener conciertos:', error); }
      );
    }
  }

  // Gestión de asientos para un concierto
  gestionarAsientos(concierto: any): void {
    this.conciertoSeleccionado = concierto;
    this.mostrarModal = true;
    // Cargar los asientos para el concierto seleccionado
    this.conciertoService.obtenerAsientosPorConcierto(concierto.idConcierto).subscribe(
      data => { this.asientos = data; },
      error => { console.error('Error al obtener asientos:', error); }
    );
  }

  // Método cuando el artista selecciona un asiento
  onAsientoSeleccionado(asiento: any): void {  
    if (!asiento.idAsiento) {
      console.error("No se encontró el ID del asiento", asiento);
      return;
    }
    
    // Al seleccionar en modo artista, marcamos el asiento como VIP
    asiento.vip = true;
    // Establecemos el tipo del asiento a 'VIP'
    asiento['tipo'] = 'VIP';
    // Asignamos el precio correspondiente a VIP: 43€
    asiento['precioVenta'] = 43;
    
    // Llamamos al servicio para actualizar el asiento en la base de datos
    this.asientoService.actualizarAsiento(asiento.idAsiento, asiento).subscribe(
      updatedAsiento => {
        console.log('Asiento actualizado:', updatedAsiento);
        // Actualizamos el arreglo local de asientos, si es necesario
        this.asientos = this.asientos.map(a => a.idAsiento === updatedAsiento.idAsiento ? updatedAsiento : a);
        this.cerrarModal();
      },
      error => {
        console.error('Error al actualizar el asiento:', error);
      }
    );
  }

  // Cerrar el modal
  cerrarModal(): void {
    this.mostrarModal = false;
  }
}
