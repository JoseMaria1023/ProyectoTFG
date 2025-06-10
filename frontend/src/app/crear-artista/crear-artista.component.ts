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
      nombre: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(50)]],
      apellidos: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(50)]],
      username: ['', [Validators.required, Validators.minLength(4), Validators.maxLength(20)]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      descripcion: ['', [Validators.maxLength(300)]],
      generoMusical: ['', [Validators.maxLength(50)]]
    });
  }

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length) {
      this.selectedFile = input.files[0];

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
          alert('Artista registrado');
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
