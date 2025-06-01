import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { AsientoService } from '../asiento.service';
import { CrearAsientoComponent } from '../crear-asiento/crear-asiento.component';
import { EditarAsientoComponent } from '../editar-asiento/editar-asiento.component';

@Component({
  selector: 'app-gestionar-asientos',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    CrearAsientoComponent,
    EditarAsientoComponent
  ],
  templateUrl: './gestionar-asiento.component.html',
  styleUrls: ['./gestionar-asiento.component.css']
})
export class GestionarAsientosComponent implements OnInit {
  asientos: any[] = [];
  mostrandoEditor = false;

  constructor(
    private asientoService: AsientoService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.cargarAsientos();
  }

  cargarAsientos(): void {
    this.asientoService.getAllAsientos().subscribe(data => {
      this.asientos = data;
    });
  }

  seleccionarAsiento(asiento: any): void {
    sessionStorage.setItem('asientoAEditar', asiento.idAsiento.toString());
    this.mostrandoEditor = true;
  }

  refrescar(): void {
    this.cargarAsientos();
    this.mostrandoEditor = false;
  }
}
