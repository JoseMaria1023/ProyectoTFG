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

  constructor(private conciertoService: ConciertoService) {}

  ngOnInit(): void {
    this.cargarConciertos();
  }

  cargarConciertos(): void {
    this.conciertoService.TraerConciertos().subscribe(data => {
      this.conciertos = data;
    });
  }

  seleccionarConcierto(concierto: any): void {
  sessionStorage.setItem('conciertoAEditar', concierto.idConcierto.toString());
  this.conciertoSeleccionado = true; 
}

  onConciertoCreado(): void {
    this.cargarConciertos();
  }

  onConciertoActualizado(): void {
    this.cargarConciertos();
    this.conciertoSeleccionado = false;
  }

  eliminarConcierto(id: number): void {
    if (confirm('¿Estás seguro de eliminar este concierto?')) {
      this.conciertoService.eliminarConcierto(id).subscribe(() => {
        this.cargarConciertos();
      });
    }
  }
}
