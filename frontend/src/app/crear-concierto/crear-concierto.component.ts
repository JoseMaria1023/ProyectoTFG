// src/app/crear-concierto/crear-concierto.component.ts
import { Component, OnInit } from '@angular/core';
import { Router }            from '@angular/router';
import { FormsModule }       from '@angular/forms';
import { CommonModule }      from '@angular/common';

import { ConciertoService }  from '../concierto.service';
import { ZonaService }       from '../zona.service';
import { GiraService }       from '../gira.service';

@Component({
  selector: 'app-crear-concierto',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './crear-concierto.component.html',
  styleUrls: ['./crear-concierto.component.css'],
})
export class CrearConciertoComponent implements OnInit {
  nombre     = '';
  fecha      = '';               
  zonaId: number | null = null;
  giraId: number | null = null;
  estado     = 'Activo';
  zonas: any[] = [];
  giras: any[] = [];

  constructor(
    private conciertoService: ConciertoService,
    private zonaService: ZonaService,
    private giraService: GiraService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.zonaService.obtenerZonas().subscribe(zs => this.zonas = zs);
    this.giraService.getAllGiras().subscribe(gs => this.giras = gs);
  }

  onSubmit(): void {
    if (!this.zonaId || !this.giraId) {
      return alert('Selecciona una zona y una gira');
    }
    if (!this.fecha) {
      return alert('Selecciona una fecha');
    }

    const fechaISO = this.fecha + 'T00:00:00';

    const nuevoConcierto = {
      nombre: this.nombre,
      fecha: fechaISO,
      zonaId: this.zonaId,
      giraId: this.giraId,
      estado: this.estado,
    };

    this.conciertoService.crearConcierto(nuevoConcierto)
      .subscribe({
        next: response => {
          console.log('Concierto creado con éxito', response);
          this.router.navigate(
            ['/Gestionar-asiento'],
            { queryParams: { conciertoId: response.idConcierto } }
          );
        },
        error: err => {
          console.error('Error al crear el concierto', err);
          alert('No se pudo crear el concierto');
        }
      });
  }
}
