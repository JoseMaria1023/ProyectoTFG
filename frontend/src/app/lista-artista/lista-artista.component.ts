import { Component, OnInit } from '@angular/core';
import { ArtistaService } from '../artista.service';
import { CommonModule } from '@angular/common';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-lista-artista',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './lista-artista.component.html',
  styleUrls: ['./lista-artista.component.css']
})
export class ListaArtistaComponent implements OnInit {
  artistas: any[] = [];
  favoritos: number[] = [];

  constructor(
    private artistaService: ArtistaService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.artistaService.TraerArtistas().subscribe({
      next: (data) => {
        this.artistas = data.map((artista: any) => ({
          ...artista,
          fotoUrl: `http://localhost:9000${artista.foto}`,
          favorito: false
        }));
      },
    });
  }

  ArtistaFavorito(artista: any): void {
    artista.favorito = !artista.favorito;
  }
}
