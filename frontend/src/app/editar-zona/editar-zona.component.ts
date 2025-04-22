import { Component, Input, OnInit } from '@angular/core';
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
  @Input() zona: any = {
    idZona: 0,
    nombre: '',
    recintoId: null,
    precioBase: 0,
    precioVIP: null
  };

  recintos: any[] = []; // Aquí se almacenará la lista de recintos obtenida desde el servicio

  constructor(
    private zonaService: ZonaService,
    private recintoService: RecintoService
  ) {}

  ngOnInit(): void {
    // Llama al servicio de recintos para obtener la lista
    this.recintoService.obtenerRecintos().subscribe(
      data => {
        this.recintos = data;
      },
      error => {
        console.error('Error al obtener la lista de recintos', error);
      }
    );
  }

  actualizarZona(): void {
    this.zonaService.actualizarZona(this.zona.idZona, this.zona).subscribe(
      response => {
        console.log('Zona actualizada:', response);
        alert('Zona actualizada con éxito');
      },
      error => {
        console.error('Error al actualizar la zona:', error);
        alert('Error al actualizar la zona');
      }
    );
  }
}
