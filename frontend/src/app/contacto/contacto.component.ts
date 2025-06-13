import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import emailjs from 'emailjs-com';

@Component({
  selector: 'app-contacto',
  standalone: true,
  imports: [CommonModule,FormsModule],
  templateUrl: './contacto.component.html',
  styleUrls: ['./contacto.component.css']
})
export class ContactoComponent {
  formData = {
    name: '',
    email: '',
    subject: '',
    message: ''
  };

  mensajeEnviado = false;
  mensajeError = false;

  enviarMensaje() {
    const serviceID = 'service_sjdv24g';
    const templateID = 'template_spai7xh';
    const publicKey = 'hGJVhtyJRuuoOoN3B'; 

    const templateParams = {
      from_name: this.formData.name,
      from_email: this.formData.email,
      subject: this.formData.subject,
      message: this.formData.message
    };

    emailjs.send(serviceID, templateID, templateParams, publicKey)
      .then(() => {
        this.mensajeEnviado = true;
        this.mensajeError = false;
        this.formData = { name: '', email: '', subject: '', message: '' };
      })
      .catch((error) => {
        this.mensajeError = true;
        this.mensajeEnviado = false;
        console.error('Error al enviar el correo:', error);
      });
  }
}
