import { Component, OnInit } from '@angular/core';
import { GiraService } from '../gira.service'; 
import { ArtistaService } from '../artista.service'; 
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CrearGiraComponent } from '../crear-gira/crear-gira.component';
import { Router } from '@angular/router';

@Component({
  selector: 'app-gestionar-gira',
  standalone: true,
  imports: [CommonModule, FormsModule, CrearGiraComponent],
  templateUrl: './gestionar-gira.component.html',
  styleUrls: ['./gestionar-gira.component.css']
})
export class GestionarGiraComponent implements OnInit {
  giras: any[] = [];

  constructor(
    private giraService: GiraService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.cargarGiras();
  }

  cargarGiras(): void {
    this.giraService.getAllGiras().subscribe(data => {
      this.giras = data;
    });
  }

  onGiraCreada(): void {
    this.cargarGiras();
  }

 editarGira(id: number): void {
  sessionStorage.setItem('giraAEditar', id.toString());
  this.router.navigate(['/Editar-gira']); // sin parámetro
}

  eliminarGira(id: number): void {
    if (confirm('¿Estás seguro de eliminar esta gira?')) {
      this.giraService.deleteGira(id).subscribe(() => {
        this.cargarGiras();
      });
    }
  }
}