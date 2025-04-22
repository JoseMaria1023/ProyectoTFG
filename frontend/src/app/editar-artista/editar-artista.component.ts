import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ArtistaService } from '../artista.service'; 

@Component({
  selector: 'app-editar-artista',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './editar-artista.component.html',
  styleUrls: ['./editar-artista.component.css']
})
export class EditarArtistaComponent {
  @Input() artista: any = {
    idArtista: 0,
    nombre: '',
    apellidos: '',
    username: '',
    password: '',
    descripcion: '',
    generoMusical: '',
    foto: ''
  };
  @Output() artistaActualizado = new EventEmitter<void>();
  @Output() cancelarEdicion = new EventEmitter<void>();

  selectedFile: File | null = null;
  fotoPreview: string | ArrayBuffer | null = null;

  constructor(private artistaService: ArtistaService) {}

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length) {
      this.selectedFile = input.files[0];

      // Previsualizar la nueva imagen
      const reader = new FileReader();
      reader.onload = () => {
        this.fotoPreview = reader.result;
      };
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

    // Se adjunta la nueva foto si se seleccionó; de lo contrario se envía el valor actual
    if (this.selectedFile) {
      formData.append('foto', this.selectedFile);
    } else {
      formData.append('foto', this.artista.foto);
    }

    this.artistaService.actualizarArtista(this.artista.idArtista, formData).subscribe(
      (response) => {
        console.log('Artista actualizado:', response);
        alert('Artista actualizado con éxito');
        this.artistaActualizado.emit();
      },
      (error) => {
        console.error('Error al actualizar artista:', error);
        alert('Error al actualizar el artista');
      }
    );
  }

  cancelar(): void {
    this.cancelarEdicion.emit();
  }
}
