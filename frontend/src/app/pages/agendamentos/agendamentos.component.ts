import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AgendamentoService } from '../../services/agendamento.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-agendamentos',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './agendamentos.component.html',
  styleUrl: './agendamentos.component.css'
})

export class AgendamentosComponent implements OnInit {
  private agendamentoService = inject(AgendamentoService);
  public authService = inject(AuthService);
  private fb = inject(FormBuilder);
  private router = inject(Router);

  agendamentos: any[] = [];
  mostrarModalEdicao = false;
  agendamentoEdicaoId: number | null = null;
  procedimentoAtualId: number | null = null;
  valorOriginal: number = 0;
  edicaoForm: FormGroup = this.fb.group({
    nomeCliente: ['', Validators.required],
    email: ['', [Validators.required, Validators.email]],
    data: ['', Validators.required],
    horario: ['', Validators.required]
  });

  ngOnInit() {
    this.carregarAgendamentos();
  }

  sair() {
    localStorage.clear();
    this.router.navigate(['/login']);
  }

  voltarParaProcedimentos() {
    this.router.navigate(['/procedimentos']);
  }

  //AGENDAMENTO
  carregarAgendamentos() {
    this.agendamentoService.listarAgendamentos().subscribe({
      next: (dados) => this.agendamentos = dados,
      error: (err) => console.error(err)
    });
  }

  atualizarStatus(id: number, novoStatus: string) {
    this.agendamentoService.mudarStatus(id, novoStatus).subscribe({
      next: () => this.carregarAgendamentos(),
      error: () => alert('Acesso Negado ou Erro na atualização.')
    });
  }

  deletar(id: number) {
    if (confirm('Tem certeza que deseja cancelar e excluir este agendamento?')) {
      this.agendamentoService.deletarAgendamento(id).subscribe({
        next: () => this.carregarAgendamentos(),
        error: () => alert('Erro ao excluir agendamento.')
      });
    }
  }

  salvarEdicao() {
    if (this.edicaoForm.invalid || !this.agendamentoEdicaoId) return;

    const payload = {
      procedimento_id: this.procedimentoAtualId,
      valorCobrado: this.valorOriginal,
      nomeCliente: this.edicaoForm.value.nomeCliente,
      email: this.edicaoForm.value.email,
      data: this.edicaoForm.value.data,
      horario: this.edicaoForm.value.horario.length === 5 ? this.edicaoForm.value.horario + ':00' : this.edicaoForm.value.horario
    };

    this.agendamentoService.atualizarAgendamento(this.agendamentoEdicaoId, payload).subscribe({
      next: () => {
        this.carregarAgendamentos();
        this.fecharModal();
      },
      error: () => alert('Erro ao atualizar agendamento.')
    });
  }

  //MODAL AGENDAMENTO
  abrirModalEditar(agendamento: any) {
    this.agendamentoEdicaoId = agendamento.agendamento_id;
    this.procedimentoAtualId = agendamento.procedimentoDTO.id;
    this.valorOriginal = agendamento.procedimentoDTO.valor;

    this.edicaoForm.patchValue({
      nomeCliente: agendamento.nomeCliente,
      data: agendamento.data,
      horario: agendamento.horario,
      email: agendamento.email
    });
    this.mostrarModalEdicao = true;
  }

  fecharModal() {
    this.mostrarModalEdicao = false;
    this.edicaoForm.reset();
  }
}