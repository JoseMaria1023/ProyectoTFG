import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-chat-asistente',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './chat-asistente.component.html',
  styleUrls: ['./chat-asistente.component.css']
})
export class ChatAsistenteComponent {
  mensajes: any[] = [
    { texto: '¡Hola! Soy tu asistente. ¿En qué puedo ayudarte hoy?', esUsuario: false }
  ];
  entrada = '';

  enviar(): void {
    const texto = this.entrada.trim();
    if (!texto) return;

    // Mensaje del usuario
    this.mensajes.push({ texto, esUsuario: true });
    this.entrada = '';

    // Respuesta simulada
    setTimeout(() => {
      const pregunta = texto.toLowerCase();
      let respuesta = 'Lo siento, no tengo esa información ahora mismo.';
      if (pregunta.includes('qr')) {
        respuesta = 'Tu QR lo puedes encontrar en la sección "Mi perfil", allí encontrarás todas tus entradas y podrás ver el QR de cada una.';
      } else if (pregunta.includes('ventas')) {
        respuesta = 'Las ventas de las entradas de los conciertos las encontrarás en la sección "Comprar entradas".';
      } else if (pregunta.includes('hola') || pregunta.includes('buenas')) {
        respuesta = '¡Hola de nuevo! ¿Quieres saber algo de tu recaudación o ventas?';
      } else if (pregunta.includes('aconsejar')) {
        respuesta = 'Soy tu asistente, puedo ayudarte con información de la propia web o con problemas que puedas tener. ¿Con qué quieres que te ayude?';
      }
      this.mensajes.push({ texto: respuesta, esUsuario: false });
    }, 500);
  }
}
