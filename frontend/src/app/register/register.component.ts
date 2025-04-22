import { Component } from '@angular/core';
import { AuthService } from '../auth.service'; 
import { Router, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  standalone: true,
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
  imports: [FormsModule, RouterModule]
})
export class RegisterComponent {
  nombre: string = '';
  apellidos: string = '';
  username: string = '';
  email: string = '';
  telefono: string = '';
  password: string = '';
  confirmPassword: string = '';

  constructor(private authService: AuthService, private router: Router) {}

  onSubmit() {
    if (this.password !== this.confirmPassword) {
      alert('Las contraseñas no coinciden');
      return;
    }

    const user = {
      nombre: this.nombre,
      apellidos: this.apellidos,
      username: this.username,
      email: this.email,
      telefono: this.telefono,
      password: this.password,
      role: 'USER',  
      activo: true
    };

    this.authService.register(user).subscribe({
      next: () => {
        alert('Usuario registrado exitosamente');
        this.router.navigate(['/login']);
      },
      error: (err) => {
        alert('Error en el registro: ' + err.error.message);
      }
    });
  }
}
