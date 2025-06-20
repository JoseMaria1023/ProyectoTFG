import { Component, OnInit } from '@angular/core';
import { GiraService } from '../gira.service';
import { ArtistaService } from '../artista.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-editar-gira',
  templateUrl: './editar-gira.component.html',
  styleUrls: ['./editar-gira.component.css'],
  standalone: true,
  imports: [CommonModule, FormsModule]
})
export class EditarGiraComponent implements OnInit {
  gira: any = { idGira: 0, nombre: '', descripcion: '', artistaId: null };
  artistas: any[] = [];

  constructor(
    private giraService: GiraService,
    private artistaService: ArtistaService,
    private router: Router
  ) {}

  ngOnInit(): void {
  const id = sessionStorage.getItem('giraAEditar');
  if (id) {
    this.giraService.getAllGiras().subscribe((data: any[]) => {
      const encontrada = data.find(gira => gira.idGira === +id);
      if (encontrada) {
        this.gira = encontrada;
      }
    });
  } else {
    alert('No se ha seleccionado ninguna gira para editar.');
    this.router.navigate(['/Gestionar-gira']);
  }

  this.artistaService.TraerArtistas().subscribe((data: any[]) => {
    this.artistas = data;
  });
}

  actualizarGira(): void {
    this.giraService.updateGira(this.gira.idGira, this.gira).subscribe(
      () => {
        alert('Gira actualizada con éxito');
        this.router.navigate(['/Gestionar-gira']);
      },
      (error) => {
        console.error('Error al actualizar gira:', error);
        alert('Error al actualizar la gira');
      }
    );
  }

 cancelar(): void {
  sessionStorage.removeItem('giraAEditar');
  window.location.reload(); 
}
}
