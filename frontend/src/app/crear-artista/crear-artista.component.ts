import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ArtistaService } from '../artista.service'; 

@Component({
  selector: 'app-crear-artista',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule],
  templateUrl: './crear-artista.component.html',
  styleUrls: ['./crear-artista.component.css']
})
export class CrearArtistaComponent {
  artistaForm: FormGroup;
  selectedFile: File | null = null;
  fotoPreview: string | ArrayBuffer | null = null;

  constructor(private fb: FormBuilder, private artistaService: ArtistaService) {
    this.artistaForm = this.fb.group({
      nombre: ['', Validators.required],
      apellidos: ['', Validators.required],
      username: ['', Validators.required],
      password: ['', Validators.required],
      descripcion: [''],
      generoMusical: ['']
      // La foto se gestionará a través de "selectedFile"
    });
  }

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length) {
      this.selectedFile = input.files[0];

      // Previsualización de la imagen
      const reader = new FileReader();
      reader.onload = () => {
        this.fotoPreview = reader.result;
      };
      reader.readAsDataURL(this.selectedFile);
    }
  }

  crearArtista(): void {
    if (this.artistaForm.valid) {
      const formData = new FormData();
      formData.append('nombre', this.artistaForm.get('nombre')?.value);
      formData.append('apellidos', this.artistaForm.get('apellidos')?.value);
      formData.append('username', this.artistaForm.get('username')?.value);
      formData.append('password', this.artistaForm.get('password')?.value);
      formData.append('descripcion', this.artistaForm.get('descripcion')?.value);
      formData.append('generoMusical', this.artistaForm.get('generoMusical')?.value);
      
      if (this.selectedFile) {
        formData.append('foto', this.selectedFile);
      }

      this.artistaService.registrarArtista(formData).subscribe(
        (response) => {
          console.log('Artista creado:', response);
          alert('Artista registrado con éxito');
          this.artistaForm.reset();
          this.selectedFile = null;
          this.fotoPreview = null;
        },
        (error) => {
          console.error('Error al crear artista:', error);
          alert('Error al registrar el artista');
        }
      );
    }
  }
}
