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
  const id = sessionStorage.getItem('zonaSeleccionadaId');

  if (id) {
    this.zonaService.TraerZonas().subscribe((data: any[]) => {
      const encontrada = data.find(zona => zona.idZona === +id);
      if (encontrada) {
        this.zona = encontrada;
      }
    });
  }

  this.recintoService.TraerRecintos().subscribe((data: any[]) => {
    this.recintos = data;
  });
}


  actualizarZona(): void {
    this.zonaService.actualizarZona(this.zona.idZona, this.zona).subscribe(
      respuesta => {
        console.log('Zona actualizada:', respuesta);
        alert('Zona actualizada con éxito');
      },
      err => {
        console.error('Error al actualizar zona:', err);
        alert('Error al actualizar la zona');
      }
    );
  }
}