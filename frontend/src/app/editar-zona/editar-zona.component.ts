import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ZonaService } from '../zona.service';
import { RecintoService } from '../recinto.service';

@Component({
  selector: 'app-editar-zona',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './editar-zona.component.html',
  styleUrls: ['./editar-zona.component.css']
})
export class EditarZonaComponent implements OnInit {
  zona: any = {
    idZona: 0,
    nombre: '',
    recintoId: null,
    precioBase: 0,
    precioVIP: null
  };
  recintos: any[] = [];

  constructor(
    private zonaService: ZonaService,
    private recintoService: RecintoService
  ) {}

  ngOnInit(): void {
    // 1) Cargamos lista de recintos
    this.recintoService.obtenerRecintos().subscribe(
      data => this.recintos = data,
      err => console.error('Error al obtener recintos', err)
    );

    // 2) Cargamos la zona a editar:
    const idZonaStr = sessionStorage.getItem('zonaSeleccionadaId');
    if (idZonaStr) {
      const idZona = Number(idZonaStr);
      this.zonaService.obtenerZonaPorId(idZona).subscribe(
        zona => this.zona = zona,
        err => {
          console.error('Error al cargar zona', err);
          alert('No se pudo cargar la zona para editar.');
        }
      );
    } else {
      console.warn('No hay zonaSeleccionadaId en sessionStorage');
    }
  }

  actualizarZona(): void {
    this.zonaService.actualizarZona(this.zona.idZona, this.zona).subscribe(
      resp => {
        console.log('Zona actualizada:', resp);
        alert('Zona actualizada con éxito');
      },
      err => {
        console.error('Error al actualizar zona:', err);
        alert('Error al actualizar la zona');
      }
    );
  }
}