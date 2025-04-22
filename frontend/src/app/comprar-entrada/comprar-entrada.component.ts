import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { EntradaService } from '../entrada.service'; 
import { PagoService } from '../pago.service';
import { AuthService } from '../auth.service';
import { ConciertoService } from '../concierto.service';
import { AsientoService } from '../asiento.service';
import { MapaAsientosComponent } from '../mapa-asientos/mapa-asientos.component';

@Component({
  selector: 'app-comprar-entrada',
  standalone: true,
  imports: [FormsModule, CommonModule, RouterModule, MapaAsientosComponent],
  templateUrl: './comprar-entrada.component.html',
  styleUrls: ['./comprar-entrada.component.css']
})
export class ComprarEntradaComponent implements OnInit {
  paso: number = 1;
  asientos: any[] = [];
  mostrarModalAsientos: boolean = false;

  // Objeto que almacena la información de la entrada a comprar.
  entrada: any = {
    tipo: 'Normal',           // Valor base (ya no usado para asignar precio)
    precioVenta: 24,          // Valor por defecto: 24€ para asientos normales
    usuarioId: null,
    conciertoId: null,
    conciertoNombre: '',
    asientoId: null,
    asientoNombre: '',
    estado: 'Disponible'
  };

  // Objeto para el pago.
  pago = {
    cantidad: 0,
    metodoPago: '',
    estado: '',             // Se establecerá a 'PENDIENTE' en el método realizarPago()
    entradaId: null,
    usuarioId: null
  };
  datosTarjeta = { numero: '', nombre: '', expiracion: '', cvv: '' };
  datosPaypal = { email: '' };
  datosTransferencia = { nombre: '', iban: '' };
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private entradaService: EntradaService,
    private pagoService: PagoService,
    private authService: AuthService,
    private conciertoService: ConciertoService,
    private asientoService: AsientoService
  ) {}

  ngOnInit(): void {
    const userId = this.authService.getUserId();
    if (userId === null) {
      alert("No se encontró el ID del usuario. Por favor, inicia sesión.");
      this.router.navigate(['/login']);
      return;
    }
    this.entrada.usuarioId = userId;

    this.route.queryParams.subscribe(params => {
      if (params['conciertoId'] && params['conciertoNombre']) {
        this.entrada.conciertoId = +params['conciertoId'];
        this.entrada.conciertoNombre = params['conciertoNombre'];
      }
    });

    if (!this.entrada.conciertoId && history.state && history.state.conciertoId) {
      this.entrada.conciertoId = +history.state.conciertoId;
      this.entrada.conciertoNombre = history.state.conciertoNombre;
    }

    console.log("Concierto asignado:", this.entrada.conciertoId, this.entrada.conciertoNombre);
  }

  abrirMapaAsientos(): void {
    if (!this.entrada.conciertoId) {
      alert('El concierto no está asignado.');
      return;
    }
    this.asientoService.obtenerAsientosPorConcierto(this.entrada.conciertoId).subscribe(
      data => {
        this.asientos = data;
        this.mostrarModalAsientos = true;
      },
      error => console.error('Error al obtener asientos:', error)
    );
  }

  // Método para seleccionar el asiento en el flujo de compra, utilizando el método
  // "seleccionarCliente" que emite el evento "asientoSeleccionado" en MapaAsientosComponent.
  onAsientoSeleccionado(asiento: any): void {
    if (!asiento.idAsiento) {
      console.error("No se encontró el ID del asiento en el objeto", asiento);
      return;
    }
    this.entrada.asientoId = asiento.idAsiento;
    this.entrada.asientoNombre = `F${asiento.fila} - C${asiento.columna}`;
    
    if (asiento.tipo === 'VIP') {
      asiento.vip = true;  // Marcar como VIP
      this.entrada.precioVenta = 43;  // Asignar precio para asiento VIP
      asiento.color = 'gold';  // Cambiar el color a dorado
    } else {
      this.entrada.precioVenta = 24;  // Asignar precio para asiento normal
      asiento.color = 'white';  // Cambiar el color a blanco para asientos normales
    }
    
    this.mostrarModalAsientos = false;
  }
  

  comprarEntrada(): void {
    if (!this.entrada.conciertoId || !this.entrada.asientoId) {
      alert('Debes seleccionar un concierto y un asiento.');
      return;
    }
  
    if (!this.pago.metodoPago) {
      alert('Selecciona un método de pago antes de continuar.');
      return;
    }
  
    this.entradaService.crearEntrada(this.entrada).subscribe(
      response => {
        console.log('Entrada creada:', response);
  
        // Configuramos datos del pago automáticamente
        this.pago.entradaId = response.idEntrada;
        this.pago.usuarioId = this.entrada.usuarioId;
        this.pago.cantidad = response.precioVenta;
        this.pago.estado = 'PENDIENTE';
  
        // Creamos el pago inmediatamente
        this.pagoService.crearPago(this.pago).subscribe(
          pagoResponse => {
            console.log('Pago realizado:', pagoResponse);
            alert('Compra completada exitosamente');
            this.router.navigate(['/mis-entradas']);
          },
          error => {
            console.error('Error al realizar el pago:', error);
            alert('Error al realizar el pago');
          }
        );
      },
      error => {
        console.error('Error al comprar la entrada:', error);
        alert('Error al comprar la entrada');
      }
    );
  }
}
