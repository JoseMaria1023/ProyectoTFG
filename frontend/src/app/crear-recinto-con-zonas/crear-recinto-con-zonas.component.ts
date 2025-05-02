import { Component } from '@angular/core';
import { RecintoService } from '../recinto.service';
import { ZonaService } from '../zona.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-crear-recinto-con-zonas',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './crear-recinto-con-zonas.component.html',
  styleUrls: ['./crear-recinto-con-zonas.component.css']
})
export class CrearRecintoConZonasComponent {
  nombreRecinto = '';
  ubicacion = '';
  capacidadTotal = 0;

  nombreZona = '';
  precioBaseZona: number | null = null;
  precioVIPZona: number | null = null;

  zonas: any[] = [];

  constructor(private recintoService: RecintoService, private zonaService: ZonaService) {}

  anadirZona() {
    if (this.nombreZona && this.precioBaseZona !== null) {
      this.zonas.push({
        nombre: this.nombreZona,
        precioBase: this.precioBaseZona,
        precioVIP: this.precioVIPZona
      });
      // Limpiar campos
      this.nombreZona = '';
      this.precioBaseZona = null;
      this.precioVIPZona = null;
    } else {
      alert('Introduce nombre y precio base de la zona');
    }
  }

  eliminarZona(index: number) {
    this.zonas.splice(index, 1);
  }

  crearRecintoConZonas() {
    const recinto = {
      nombre: this.nombreRecinto,
      ubicacion: this.ubicacion,
      capacidadTotal: this.capacidadTotal
    };

    // 1. Crear recinto
    this.recintoService.crearRecinto(recinto).subscribe(
      (recintoCreado: any) => {
        console.log('Recinto creado:', recintoCreado);

        // 2. Crear zonas asociadas
        const recintoId = recintoCreado.idRecinto;
        const zonasRequests = this.zonas.map(zona => {
          return this.zonaService.crearZona({
            nombre: zona.nombre,
            recintoId: recintoId,
            precioBase: zona.precioBase,
            precioVIP: zona.precioVIP
          });
        });

        Promise.all(zonasRequests.map(request => request.toPromise()))
          .then(results => {
            console.log('Todas las zonas creadas:', results);
            alert('Recinto y zonas creadas correctamente');
            // Limpiar todo
            this.nombreRecinto = '';
            this.ubicacion = '';
            this.capacidadTotal = 0;
            this.zonas = [];
          })
          .catch(error => {
            console.error('Error al crear zonas', error);
            alert('Error al crear las zonas');
          });
      },
      error => {
        console.error('Error al crear recinto', error);
        alert('Error al crear el recinto');
      }
    );
  }
}
