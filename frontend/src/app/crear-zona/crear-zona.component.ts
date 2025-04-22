import { Component } from '@angular/core';
import { ZonaService } from '../zona.service';
import { RecintoService } from '../recinto.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-crear-zona',
  standalone: true,
  imports: [FormsModule, CommonModule, RouterModule],
  templateUrl: './crear-zona.component.html',
  styleUrls: ['./crear-zona.component.css']
})
export class CrearZonaComponent {
  nombre: string = '';
  recinto_id: number | null = null;
  precioBase: number = 0;
  precioVIP: number | null = null;
  recintos: any[] = []; // Lista de recintos disponibles

  constructor(private zonaService: ZonaService, private recintoService: RecintoService) {}

  ngOnInit() {
    // Cargar recintos disponibles para selección
    this.recintoService.obtenerRecintos().subscribe(data => {
      this.recintos = data;
    }, error => {
      console.error('Error al obtener recintos', error);
    });
  }

  onSubmit() {
    if (!this.recinto_id) {
      alert('Selecciona un recinto');
      return;
    }
  
    const nuevaZona = {
      nombre: this.nombre,
      recintoId: this.recinto_id,
      precioBase: this.precioBase,
      precioVIP: this.precioVIP
    };
  
    this.zonaService.crearZona(nuevaZona).subscribe(response => {
      console.log('Zona creada con éxito', response);
      alert('Zona creada con éxito');
      // Reinicia los campos, si es necesario
      this.nombre = '';
      this.recinto_id = null;
      this.precioBase = 0;
      this.precioVIP = null;
    }, error => {
      console.error('Error al crear la zona', error);
      alert('Error al crear la zona');
    });
  }
}
