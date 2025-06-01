import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { EntradaService } from '../entrada.service';
import { PagoService } from '../pago.service';
import { AuthService } from '../auth.service';
import { AsientoService } from '../asiento.service';
import { UsuarioService } from '../usuario.service';
import { ZonaService } from '../zona.service';
import { MapaAsientosComponent } from '../mapa-asientos/mapa-asientos.component';

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

  precioBaseZona = 0;
  precioVipZona  = 0;

  entrada: any = {
    tipo: 'Normal',
    precioVenta: 0,
    usuarioId: null as number | null,
    conciertoId: null as number | null,
    conciertoNombre: '',
    asientosSeleccionados: [] as any[],
    cantidadEntradas: 0,
    estado: 'Disponible'
  };

  pago: any = {
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
    private usuarioService: UsuarioService,
    private zonaService: ZonaService
  ) {}

  ngOnInit(): void {
    const userId = this.authService.getUserId();
    if (!userId) {
      alert('Debes iniciar sesión.');
      this.router.navigate(['/login']);
      return;
    }

    this.usuarioService.getUsuarioById(userId).subscribe({
      next: (usuario: any) => {
        if (!usuario.activo) {
          alert('Cuenta desactivada. Actívala para comprar entradas.');
          this.router.navigate(['/home']);
          return;
        }

        this.entrada.usuarioId = userId;
        this.pago.usuarioId   = userId;

        this.route.queryParams.subscribe((params: any) => {
          this.entrada.conciertoId = +params['conciertoId'] || +history.state.conciertoId;
          this.entrada.conciertoNombre = params['conciertoNombre'] || history.state.conciertoNombre;

          if (this.entrada.conciertoId) {
            this.zonaService.getZonaPorConcierto(this.entrada.conciertoId).subscribe({
              next: (zona: any) => {
                this.precioBaseZona = Number(zona.precioBase);
                this.precioVipZona  = Number(zona.precioVIP);
              },
              error: () => console.error('Error al cargar la zona')
            });
          }
        });
      },
      error: () => {
        alert('Error al verificar usuario.');
        this.router.navigate(['/login']);
      }
    });
  }

  abrirMapaAsientos(): void {
    if (!this.entrada.conciertoId) {
      alert('Concierto no asignado.');
      return;
    }
    this.asientoService.TraerAsientosPorConcierto(this.entrada.conciertoId).subscribe({
      next: (data: any[]) => {
        this.asientos = data;
        this.mostrarModalAsientos = true;
      },
      error: (err: any) => console.error('Error al obtener asientos:', err)
    });
  }

onAsientoSeleccionado(asiento: any): void {
  if (asiento.ocupado || !asiento.idAsiento) {
    alert('Asiento ocupado o inválido.');
    return;
  }

  const seleccionados = this.entrada.asientosSeleccionados;
  const esSeleccionado = seleccionados.some((a: any) => a.idAsiento === asiento.idAsiento);

  if (esSeleccionado) {
    this.entrada.asientosSeleccionados = seleccionados.filter((a: any) => a.idAsiento !== asiento.idAsiento);
  } else {
    const precio = asiento.tipo === 'VIP' ? this.precioVipZona : this.precioBaseZona;
    this.entrada.asientosSeleccionados.push({
      idAsiento: asiento.idAsiento,
      nombre: `F${asiento.fila} - C${asiento.columna}`,
      precioUnitario: precio
    });
  }

  const nuevos = this.entrada.asientosSeleccionados;
  this.entrada.tipo = nuevos.some((a: any) => a.precioUnitario === this.precioVipZona) ? 'VIP' : 'Normal';
  this.entrada.cantidadEntradas = nuevos.length;
  this.entrada.precioVenta = nuevos.reduce((total: number, a: any) => total + a.precioUnitario, 0);
  this.mostrarModalAsientos = false;
}


  comprarEntradas(): void {
    if (!this.entrada.usuarioId || !this.entrada.conciertoId) {
      alert('Faltan datos del usuario o concierto.');
      return;
    }
    if (this.entrada.asientosSeleccionados.length === 0) {
      alert('Debes seleccionar al menos un asiento.');
      return;
    }
    if (!this.pago.metodoPago) {
      alert('Selecciona un método de pago.');
      return;
    }

    switch (this.pago.metodoPago) {
      case 'Tarjeta':
        if (!this.datosTarjeta.numero || !this.datosTarjeta.nombre || !this.datosTarjeta.expiracion || !this.datosTarjeta.cvv) {
          alert('Completa todos los campos de la tarjeta.');
          return;
        }
        if (!/^\d{16}$/.test(this.datosTarjeta.numero)) {
          alert('Número de tarjeta inválido. Debe tener 16 dígitos.');
          return;
        }
        if (!/^\d{3,4}$/.test(this.datosTarjeta.cvv)) {
          alert('CVV inválido.');
          return;
        }
        break;

      case 'PayPal':
        if (!this.datosPaypal.email) {
          alert('Introduce tu correo de PayPal.');
          return;
        }
        if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(this.datosPaypal.email)) {
          alert('Correo de PayPal no válido.');
          return;
        }
        break;

      case 'Transferencia':
        if (!this.datosTransferencia.nombre || !this.datosTransferencia.iban) {
          alert('Completa todos los campos de transferencia.');
          return;
        }
        if (!/^([A-Z]{2}\d{2}[A-Z0-9]{1,30})$/.test(this.datosTransferencia.iban)) {
          alert('IBAN inválido.');
          return;
        }
        break;

      default:
        alert('Método de pago no válido.');
        return;
    }

    const entradasPayload = this.entrada.asientosSeleccionados.map((a: any) => ({
      tipo: this.entrada.tipo,
      precioVenta: a.precioUnitario,
      usuarioId: this.entrada.usuarioId,
      conciertoId: this.entrada.conciertoId,
      asientoId: a.idAsiento,
      estado: 'PENDIENTE'
    }));

    this.entradasProcesadas = 0;
    entradasPayload.forEach((entrada: any) => {
      this.entradaService.crearEntrada(entrada).subscribe({
        next: (eCreada: any) => {
          const pagoObj = {
            entradaId: eCreada.idEntrada,
            usuarioId: this.pago.usuarioId,
            cantidad: eCreada.precioVenta,
            metodoPago: this.pago.metodoPago,
            estado: 'PENDIENTE'
          };
          this.pagoService.crearPago(pagoObj).subscribe({
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
  get asientosSeleccionados(): any[] {
  return this.entrada.asientosSeleccionados;
}

get hayAsientosSeleccionados(): boolean {
  return this.asientosSeleccionados.length > 0;
}

get precioUnitarioActual(): number {
  return this.hayAsientosSeleccionados ? this.asientosSeleccionados[this.asientosSeleccionados.length - 1].precioUnitario : this.precioBaseZona;
}

get mostrarPagoTarjeta(): boolean {
  return this.pago.metodoPago === 'Tarjeta';
}

get mostrarPagoPaypal(): boolean {
  return this.pago.metodoPago === 'PayPal';
}

get mostrarPagoTransferencia(): boolean {
  return this.pago.metodoPago === 'Transferencia';
}

}
