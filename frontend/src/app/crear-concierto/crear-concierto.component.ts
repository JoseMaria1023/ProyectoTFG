import { Component } from '@angular/core';
import { ConciertoService } from '../concierto.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ZonaService } from '../zona.service';
import { GiraService } from '../gira.service';

@Component({
  selector: 'app-crear-concierto',
  standalone: true,
  imports: [FormsModule, CommonModule, RouterModule],
  templateUrl: './crear-concierto.component.html',
  styleUrls: ['./crear-concierto.component.css'],
})
export class CrearConciertoComponent {
  nombre: string = '';
  fecha: string = '';
  zonaId: number | null = null;
  giraId: number | null = null;
  estado: string = 'Activo';
  zonas: any[] = [];
  giras: any[] = [];

  constructor(
    private conciertoService: ConciertoService,
    private zonaService: ZonaService,
    private giraService: GiraService
  ) {}

  ngOnInit() {
    this.zonaService.obtenerZonas().subscribe((data) => {
      this.zonas = data;
    });
    this.giraService.getAllGiras().subscribe((data) => {
      this.giras = data;
    });
  }

  onSubmit() {
    if (!this.zonaId || !this.giraId) {
      alert('Selecciona una zona y una gira');
      return;
    }

    const nuevoConcierto = {
      nombre: this.nombre,
      fecha: this.fecha,
      zonaId: this.zonaId,
      giraId: this.giraId,
      estado: this.estado,
    };

    this.conciertoService.crearConcierto(nuevoConcierto).subscribe(
      (response) => {
        console.log('Concierto creado con éxito', response);
      },
      (error) => {
        console.error('Error al crear el concierto', error);
      }
    );
  }
}
