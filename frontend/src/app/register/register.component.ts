import { Component } from '@angular/core';
import { AuthService } from '../auth.service';
import { Router, RouterModule } from '@angular/router';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';

@Component({
  standalone: true,
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
  imports: [ReactiveFormsModule, RouterModule, CommonModule, HttpClientModule]
})
export class RegisterComponent {
  registerForm: FormGroup;

  constructor(private formbuild: FormBuilder, private authService: AuthService, private router: Router) {
    this.registerForm = this.formbuild.group({
      nombre: ['', Validators.required],
      apellidos: ['', Validators.required],
      username: ['', [Validators.required, Validators.minLength(4)]],
      email: ['', [Validators.required, Validators.email]],
      telefono: [''],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', Validators.required]
    }, { validators: this.passwordsMatchValidator });
  }

  passwordsMatchValidator(form: FormGroup) {
    const password = form.get('password')?.value;
    const confirmPassword = form.get('confirmPassword')?.value;
    return password === confirmPassword ? null : { passwordsMismatch: true };
  }

  onSubmit() {
    if (this.registerForm.invalid) {
      this.registerForm.markAllAsTouched();
      return;
    }

    const formValues = this.registerForm.value;

    const user = {
      nombre: formValues.nombre,
      apellidos: formValues.apellidos,
      username: formValues.username,
      email: formValues.email,
      telefono: formValues.telefono,
      password: formValues.password,
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

  get f() {
    return this.registerForm.controls;
  }
}
