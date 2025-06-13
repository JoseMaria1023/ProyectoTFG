import { Component, OnInit } from '@angular/core';
import { ConciertoService } from '../concierto.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CrearConciertoComponent } from '../crear-concierto/crear-concierto.component';
import { EditarConciertoComponent } from '../editar-concierto/editar-concierto.component';

@Component({
  selector: 'app-gestionar-conciertos',
  standalone: true,
  imports: [CommonModule, FormsModule, CrearConciertoComponent, EditarConciertoComponent],
  templateUrl: './gestionar-concierto.component.html',
  styleUrls: ['./gestionar-concierto.component.css']
})
export class GestionarConciertosComponent implements OnInit {
  conciertos: any[] = [];
  conciertoSeleccionado: boolean = false;

  estados: string[] = ['ACTIVO', 'CANCELADO', 'POSPUESTO'];
  estadoFiltro: string = '';

  constructor(private conciertoService: ConciertoService) {}

  ngOnInit(): void {
    this.cargarConciertos();
  }

  cargarConciertos(): void {
    if (!this.estadoFiltro) {
      this.conciertoService.TraerConciertos().subscribe(data => {
        this.conciertos = data;
      });
    } else {
      this.conciertoService.filtrarConciertos({ estado: this.estadoFiltro })
        .subscribe(data => this.conciertos = data);
    }
  }

  onEstadoChange(): void {
    this.cargarConciertos();
  }

  seleccionarConcierto(concierto: any): void {
    sessionStorage.setItem('conciertoAEditar', concierto.idConcierto.toString());
    this.conciertoSeleccionado = true; 
  }

  onConciertoCreado(): void {
    this.estadoFiltro = '';        
    this.cargarConciertos();
  }

  onConciertoActualizado(): void {
    this.estadoFiltro = ''; 
    this.cargarConciertos();
    this.conciertoSeleccionado = false;
  }

  eliminarConcierto(id: number): void {
    if (confirm('¿Estás seguro de eliminar este concierto?')) {
      this.conciertoService.eliminarConcierto(id)
        .subscribe(() => this.cargarConciertos());
    }
  }
}