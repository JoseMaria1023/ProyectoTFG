import { Component, OnInit } from '@angular/core';
import { AsientoService } from '../asiento.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CrearAsientoComponent } from '../crear-asiento/crear-asiento.component';
import { EditarAsientoComponent } from '../editar-asiento/editar-asiento.component';

@Component({
  selector: 'app-gestionar-asientos',
  standalone: true,
  imports: [CommonModule, FormsModule, CrearAsientoComponent, EditarAsientoComponent],
  templateUrl: './gestionar-asiento.component.html',
  styleUrls: ['./gestionar-asiento.component.css']
})
export class GestionarAsientosComponent implements OnInit {
  asientos: any[] = [];
  asientoSeleccionado: any = null;
  // Si es necesario, se puede filtrar por concierto; en este ejemplo listamos todos.
  
  constructor(private asientoService: AsientoService) {}

  ngOnInit(): void {
    this.cargarAsientos();
  }

  cargarAsientos(): void {
    this.asientoService.getAllAsientos().subscribe(data => {
      this.asientos = data;
    });
  }

  seleccionarAsiento(asiento: any): void {
    // Clonar para evitar mutaciones directas de la lista
    this.asientoSeleccionado = { ...asiento };
  }

  // Si se actualiza o se elimina, se recarga la lista y se limpia el asiento seleccionado
  refrescar(): void {
    this.cargarAsientos();
    this.asientoSeleccionado = null;
  }
}
