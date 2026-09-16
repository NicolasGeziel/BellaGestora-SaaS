import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-registro',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './registro.component.html',
  styleUrl: './registro.component.css'
})
export class RegistroComponent {
  private fb = inject(FormBuilder);
  private authService = inject(AuthService);
  private router = inject(Router);

  registroForm: FormGroup = this.fb.group({
    login: ['', Validators.required],
    password: ['', [Validators.required, Validators.minLength(6)]],
    role: ['USER', Validators.required] 
  });

  registrar() {
    if (this.registroForm.invalid) return;

    this.authService.register(this.registroForm.value).subscribe({
      next: () => {
        alert('Conta criada com sucesso! Faça o login para acessar o sistema.');
        this.router.navigate(['/login']);
      },
      error: (err) => {
        console.error(err);
        alert('Erro ao criar conta. Este usuário já existe ou os dados são inválidos.');
      }
    });
  }

  irParaLogin() {
    this.router.navigate(['/login']);
  }
}