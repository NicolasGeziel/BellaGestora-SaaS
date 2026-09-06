import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AgendamentoService } from '../../services/agendamento.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-agendamentos',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './agendamentos.component.html',
  styleUrl: './agendamentos.component.css'
})
export class AgendamentosComponent implements OnInit {
  private agendamentoService = inject(AgendamentoService);
  public authService = inject(AuthService);

  agendamentos: any[] = [];
  isAdmin: boolean = false;

  ngOnInit() {
    this.isAdmin = this.authService.isAdmin();
    this.carregarAgendamentos();
  }

  carregarAgendamentos() {
    this.agendamentoService.listarAgendamentos().subscribe({
      next: (dados) => this.agendamentos = dados,
      error: (err) => console.error(err)
    });
  }

  atualizarStatus(id: number, novoStatus: string) {
    this.agendamentoService.mudarStatus(id, novoStatus).subscribe({
      next: () => {
        this.carregarAgendamentos();
      },
      error: (err) => alert('Acesso Negado ou Erro na atualização.')
    });
  }
}