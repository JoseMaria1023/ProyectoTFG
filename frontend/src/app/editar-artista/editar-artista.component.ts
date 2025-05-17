// editar-artista.component.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ArtistaService } from '../artista.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-editar-artista',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './editar-artista.component.html',
  styleUrls: ['./editar-artista.component.css']
})
export class EditarArtistaComponent implements OnInit {
  artista: any = {
    idArtista: 0,
    nombre: '',
    apellidos: '',
    username: '',
    password: '',
    descripcion: '',
    generoMusical: '',
    foto: ''
  };
  selectedFile: File | null = null;
  fotoPreview: string | ArrayBuffer | null = null;

  constructor(
    private artistaService: ArtistaService,
    private router: Router
  ) {}

  ngOnInit(): void {
    const idStr = sessionStorage.getItem('artistaAEditar');
    if (!idStr) {
      // si no hay ID, volvemos al listado
      this.router.navigate(['/Gestionar-artista']);
      return;
    }
    const id = +idStr;
    this.artistaService.getArtistaPorId(id).subscribe(
      data => this.artista = data,
      err => {
        console.error('No se pudo cargar artista', err);
        this.router.navigate(['/Gestionar-artista']);
      }
    );
  }

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length) {
      this.selectedFile = input.files[0];
      const reader = new FileReader();
      reader.onload = () => this.fotoPreview = reader.result;
      reader.readAsDataURL(this.selectedFile);
    }
  }

  guardarCambios(): void {
    const formData = new FormData();
    formData.append('nombre', this.artista.nombre);
    formData.append('apellidos', this.artista.apellidos);
    formData.append('username', this.artista.username);
    formData.append('password', this.artista.password);
    formData.append('descripcion', this.artista.descripcion);
    formData.append('generoMusical', this.artista.generoMusical);
    if (this.selectedFile) {
      formData.append('foto', this.selectedFile);
    } 

    this.artistaService.actualizarArtista(this.artista.idArtista, formData)
      .subscribe(
        () => {
          alert('Artista actualizado con éxito');
          sessionStorage.removeItem('artistaAEditar');
          this.router.navigate(['/Gestionar-artista']);
        },
        error => {
          console.error('Error al actualizar artista:', error);
          alert('Error al actualizar el artista');
        }
      );
  }

  cancelar(): void {
    sessionStorage.removeItem('artistaAEditar');
    this.router.navigate(['/Gestionar-artista']);
  }
}
