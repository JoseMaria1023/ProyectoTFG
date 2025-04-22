import { Component } from '@angular/core';
import { AuthService } from '../auth.service'; 
import { Router, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

@Component({
  standalone: true,
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  imports: [FormsModule, HttpClientModule, RouterModule]
})
export class LoginComponent {
  username: string = '';
  password: string = '';

  constructor(private authService: AuthService, private router: Router) {}

  onSubmit() {
    const credentials = {
      username: this.username,
      password: this.password
    };

    this.authService.login(credentials).subscribe({
      next: (response) => {
        this.authService.setSession(response);  // Guarda datos en sessionStorage
        this.router.navigate(['/']);
      },
      error: (err) => {
        alert('Error en el inicio de sesión: ' + err.error.message);
      }
    });
  }
}
