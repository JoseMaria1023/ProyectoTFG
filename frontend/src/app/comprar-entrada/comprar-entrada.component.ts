import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';

import { EntradaService } from '../entrada.service'; 
import { PagoService } from '../pago.service';
import { AuthService } from '../auth.service';
import { AsientoService } from '../asiento.service';
import { UsuarioService } from '../usuario.service';
import { MapaAsientosComponent } from '../mapa-asientos/mapa-asientos.component';

interface AsientoSeleccionado {
  idAsiento: number;
  nombre: string;
}

@Component({
  selector: 'app-comprar-entrada',
  standalone: true,
  imports: [FormsModule, CommonModule, RouterModule, MapaAsientosComponent],
  templateUrl: './comprar-entrada.component.html',
  styleUrls: ['./comprar-entrada.component.css']
})
export class ComprarEntradaComponent implements OnInit {
  asientos: any[] = [];
  mostrarModalAsientos = false;
  entradasProcesadas = 0;

  entrada = {
    tipo: 'Normal',
    precioUnitario: 24,
    precioVenta: 24,
    usuarioId: null as number | null,
    conciertoId: null as number | null,
    conciertoNombre: '',
    asientosSeleccionados: [] as AsientoSeleccionado[],
    cantidadEntradas: 1,
    estado: 'Disponible'
  };

  pago = {
    cantidad: 0,
    metodoPago: '',
    estado: '',
    usuarioId: null as number | null
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
    private asientoService: AsientoService,
    private usuarioService: UsuarioService 
  ) {}

  ngOnInit(): void {
    const userId = this.authService.getUserId();
    if (!userId) {
      alert("Debes iniciar sesión.");
      this.router.navigate(['/login']);
      return;
    }

    this.usuarioService.getUsuarioById(userId).subscribe({
      next: usuario => {
        if (!usuario.activo) {
          alert('Cuenta desactivada. Actívala para comprar entradas.');
          this.router.navigate(['/home']);
          return;
        }

        this.entrada.usuarioId = userId;
        this.pago.usuarioId = userId;

        this.route.queryParams.subscribe(params => {
          this.entrada.conciertoId = +params['conciertoId'] || +history.state.conciertoId;
          this.entrada.conciertoNombre = params['conciertoNombre'] || history.state.conciertoNombre;
        });
      },
      error: () => {
        alert('Error al verificar usuario.');
        this.router.navigate(['/login']);
      }
    });
  }

  abrirMapaAsientos(): void {
    if (!this.entrada.conciertoId) return alert('Concierto no asignado.');
    this.asientoService.obtenerAsientosPorConcierto(this.entrada.conciertoId)
      .subscribe({
        next: data => {
          this.asientos = data;
          this.mostrarModalAsientos = true;
        },
        error: err => console.error('Error al obtener asientos:', err)
      });
  }

  onAsientoSeleccionado(asiento: any): void {
    if (asiento.ocupado || !asiento.idAsiento) return alert('Asiento ocupado o inválido.');

    const yaSeleccionado = this.entrada.asientosSeleccionados
      .some(a => a.idAsiento === asiento.idAsiento);
    
    if (!yaSeleccionado) {
      this.entrada.asientosSeleccionados.push({
        idAsiento: asiento.idAsiento,
        nombre: `F${asiento.fila} - C${asiento.columna}`
      });
    }

    this.entrada.cantidadEntradas = this.entrada.asientosSeleccionados.length;
    this.entrada.precioVenta = this.entrada.precioUnitario * this.entrada.cantidadEntradas;
    this.mostrarModalAsientos = false;
  }

  comprarEntradas(): void {
    if (!this.entrada.usuarioId || !this.entrada.conciertoId || !this.pago.metodoPago) {
      return alert('Completa todos los datos antes de comprar.');
    }

    if (this.entrada.asientosSeleccionados.length === 0) {
      return alert('Selecciona al menos un asiento.');
    }

    const entradasPayload = this.entrada.asientosSeleccionados.map(asiento => ({
      tipo: this.entrada.tipo,
      precioVenta: this.entrada.precioUnitario,
      usuarioId: this.entrada.usuarioId!,
      conciertoId: this.entrada.conciertoId!,
      asientoId: asiento.idAsiento,
      estado: 'PENDIENTE'
    }));

    this.entradasProcesadas = 0;

    entradasPayload.forEach(entrada => {
      this.entradaService.crearEntrada(entrada).subscribe({
        next: entradaCreada => {
          const pago = {
            entradaId: entradaCreada.idEntrada,
            usuarioId: this.pago.usuarioId!,
            cantidad: entradaCreada.precioVenta,
            metodoPago: this.pago.metodoPago,
            estado: 'PENDIENTE'
          };

          this.pagoService.crearPago(pago).subscribe({
            next: () => {
              this.entradasProcesadas++;
              if (this.entradasProcesadas === entradasPayload.length) {
                alert('Compra completada exitosamente');
                this.router.navigate(['/mi-perfil']);
              }
            },
            error: () => alert('Error al procesar el pago.')
          });
        },
        error: () => alert('Error al crear una entrada.')
      });
    });
  }
}
