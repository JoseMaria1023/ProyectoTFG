import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-navbar',
  imports: [CommonModule,FormsModule],
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {
  isSidebarOpen = false;

  constructor(private router: Router, private authService: AuthService) {}

  toggleSidebar(): void {
    this.isSidebarOpen = !this.isSidebarOpen;
  }

  navigateToLogin(): void {
    this.router.navigate(['/login']);
    this.toggleSidebar();
  }

  navigateToRegister(): void {
    this.router.navigate(['/register']);
    this.toggleSidebar();
  }

  navigateToHome(): void {
    this.router.navigate(['/home']);
    this.toggleSidebar();
  }

  navigateToContacto(): void {
    this.router.navigate(['/contacto']);
    this.toggleSidebar();
  }


  navigateListarArtista(): void {
    this.router.navigate(['/Listar-artista']);
    this.toggleSidebar();
  }

  navigateToComprar(): void {
    this.router.navigate(['/Elegir-concierto']);
    this.toggleSidebar();
  }
  navigateToAsientos(): void {
    this.router.navigate(['/Gestionar-asiento']);
    this.toggleSidebar();
  }
  navigateToPerfil(): void {
    this.router.navigate(['/mi-perfil']);
    this.toggleSidebar();
  }

  navigateToGira(): void {
    this.router.navigate(['/Gestionar-gira']);
    this.toggleSidebar();
  }

  navigateToArtista(): void {
    this.router.navigate(['/Gestionar-artista']);
    this.toggleSidebar();
  }

  navigateToZonasRecinto(): void {
    this.router.navigate(['/Crear-zonas-recintos']);
    this.toggleSidebar();
  }

  navigateToZona(): void {
    this.router.navigate(['/Gestionar-concierto']);
    this.toggleSidebar();
  }

  navigateToGestionarAsientos(): void {
    this.router.navigate(['/Gestionar-asiento-artista']);
    this.toggleSidebar();
  }

  isLoggedIn(): boolean {
    return this.authService.isLoggedIn();
  }

  isArtista(): boolean {
    return this.authService.getUserRole() === 'ROLE_ARTISTA';
  }

  isAdmin(): boolean {
    return this.authService.getUserRole() === 'ROLE_ADMIN';
  }
  isUser(): boolean {
    return this.authService.getUserRole() === 'USER';
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/']);
    this.toggleSidebar();
  }
}
