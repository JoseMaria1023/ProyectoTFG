import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { GiraService } from '../gira.service';
import { ArtistaService } from '../artista.service';

@Component({
  selector: 'app-crear-gira',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './crear-gira.component.html',
  styleUrls: ['./crear-gira.component.css']
})
export class CrearGiraComponent implements OnInit {
  artistas: any[] = [];
  gira: any = { nombre: '', descripcion: '', artistaId: null };

  constructor(
    private giraService: GiraService,
    private artistaService: ArtistaService
  ) {}

  ngOnInit(): void {
    this.cargarArtistas();
  }

  cargarArtistas(): void {
    this.artistaService.TraerArtistas().subscribe(
      (data) => {
        this.artistas = data;
      },
      (error) => {
        console.error('Error al cargar artistas:', error);
        alert('Error al cargar la lista de artistas');
      }
    );
  }

  crearGira(): void {
    this.giraService.createGira(this.gira).subscribe(
      (response) => {
        console.log('Gira creada:', response);
        alert('Gira creada con éxito');
        this.resetForm();
      },
      (error) => {
        console.error('Error al crear gira:', error);
        alert('Error al crear la gira');
      }
    );
  }

  resetForm(): void {
    this.gira = { nombre: '', descripcion: '', artistaId: null };
  }
}