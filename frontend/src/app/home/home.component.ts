import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule, Router } from '@angular/router';
import { ConciertoService } from '../concierto.service';
import { ChatAsistenteComponent } from '../chat-asistente/chat-asistente.component';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule,ChatAsistenteComponent],
})
export class HomeComponent implements OnInit {
  conciertos: any[] = [];

  constructor(
    private conciertoService: ConciertoService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.cargarConciertos();
  }

  cargarConciertos(): void {
    this.conciertoService.TraerConciertos().subscribe({
      next: (data) => {
        // Filtramos para mantener solo aquellos conciertos cuya fecha no haya pasado
        const ahora = new Date();
        this.conciertos = data.filter(concierto => {
          const fechaConcierto = new Date(concierto.fecha);
          return fechaConcierto >= ahora;
        });
      },
      error: (err) => console.error('Error al obtener conciertos:', err),
    });
  }

  onComprar(conciertoId?: number): void {
    const token = sessionStorage.getItem('token');
    if (!token) {
      alert('Si no estás logueado no puedes comprar. Vas a ser redirigido al login.');
      this.router.navigate(['/login']);
      return;
    }

    if (conciertoId != null) {
      this.router.navigate(['/Elegir-concierto'], { queryParams: { id: conciertoId } });
    } else {
      this.router.navigate(['/Elegir-concierto']);
    }
  }
}
