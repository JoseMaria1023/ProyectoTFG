import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ConciertoService } from '../concierto.service';
import { ZonaService } from '../zona.service';
import { GiraService } from '../gira.service';

@Component({
  selector: 'app-editar-concierto',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './editar-concierto.component.html',
  styleUrls: ['./editar-concierto.component.css']
})
export class EditarConciertoComponent {
  @Input() concierto: any = {
    idConcierto: 0,
    nombre: '',
    fecha: '',
    zonaId: null,
    giraId: null,
    estado: 'Activo'
  };

  zonas: any[] = [];
  giras: any[] = [];

  constructor(
    private conciertoService: ConciertoService,
    private zonaService: ZonaService,
    private giraService: GiraService
  ) {}

  ngOnInit(): void {
    // Cargar las listas de zonas y giras para los select
    this.zonaService.obtenerZonas().subscribe(data => {
      this.zonas = data;
    });
    this.giraService.getAllGiras().subscribe(data => {
      this.giras = data;
    });
  }

  actualizarConcierto(): void {
    this.conciertoService.actualizarConcierto(this.concierto.idConcierto, this.concierto)
      .subscribe(
        response => {
          console.log('Concierto actualizado:', response);
          alert('Concierto actualizado con éxito');
        },
        error => {
          console.error('Error al actualizar el concierto:', error);
          alert('Error al actualizar el concierto');
        }
      );
  }
}
