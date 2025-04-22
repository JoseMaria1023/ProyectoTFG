import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../auth.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-navbar',
  imports: [RouterModule,CommonModule],
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {
  
  constructor(private router: Router, private authService: AuthService) {}

  navigateToLogin(): void {
    this.router.navigate(['/login']);
  }

  navigateToRegister(): void {
    this.router.navigate(['/register']);
  }
  navigateToPerfil(): void {
    this.router.navigate(['/mi-perfil']);
  }
  

  navigateToGira(): void {
    this.router.navigate(['/Gestionar-gira']);
  }

  navigateToRecinto(): void {
    this.router.navigate(['/Elegir-concierto']);
  }

  navigateToZona(): void {
    this.router.navigate(['/Gestionar-concierto']);
  }

  isUser(): boolean {
    return this.authService.isUser();
  }

  isLoggedIn(): boolean {
    return this.authService.isLoggedIn();
  }
  isArtista(): boolean {
    return this.authService.getUserRole() === 'ROLE_ARTISTA';
  }
  
  navigateToGestionarAsientos(): void {
    this.router.navigate(['/Gestionar-asiento-artista']);
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}
