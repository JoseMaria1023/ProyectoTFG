import { Component, OnInit } from '@angular/core';
import { ArtistaService } from '../artista.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-lista-artista',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './lista-artista.component.html',
  styleUrls: ['./lista-artista.component.css']
})
export class ListaArtistaComponent implements OnInit {
  artistas: any[] = [];

  constructor(private artistaService: ArtistaService) {}

  ngOnInit(): void {
    this.artistaService.TraerArtistas().subscribe({
      next: (data) => {
        console.log('Artistas lista:', data);
        this.artistas = data.map((artista: any) => ({ ...artista,
          fotoUrl: `http://localhost:9000${artista.foto}`
        }));
      },
      error: (err) => console.error('Error al cargar artistas:', err)
    });
  }
}
