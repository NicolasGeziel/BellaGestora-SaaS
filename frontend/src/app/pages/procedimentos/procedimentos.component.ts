import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ProcedimentoService } from '../../services/procedimento.service';
import { AgendamentoService } from '../../services/agendamento.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-procedimentos',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './procedimentos.component.html',
  styleUrl: './procedimentos.component.css'
})

export class ProcedimentosComponent implements OnInit {
  private procedimentoService = inject(ProcedimentoService);
  public authService = inject(AuthService);
  private fb = inject(FormBuilder);
  private router = inject(Router);
  private agendamentoService = inject(AgendamentoService);

  procedimentos: any[] = [];
  isAdmin: boolean = false;
  mostrarModal: boolean = false;
  modoEdicao: boolean = false;
  procedimentoAtualId: number | null = null;
  mostrarModalAgendamento: boolean = false;
  procedimentoSelecionado: any = null;
  procedimentosOriginais: any[] = [];
  procedimentoForm: FormGroup = this.fb.group({
    nome: ['', Validators.required],
    descricao: ['', Validators.required],
    observacaoDono: ['', Validators.required],
    valor: ['', [Validators.required, Validators.min(0)]]
  });
  agendamentoForm: FormGroup = this.fb.group({
  nomeCliente: ['', Validators.required],
  email: ['', [Validators.required, Validators.email]],
  data: ['', Validators.required],
  horario: ['', Validators.required]
  });

  //CREDENCIAL
  ngOnInit(): void {
    this.isAdmin = this.authService.isAdmin();
    this.carregarProcedimentos();
  }

  //NAVEGACAO
  sair() {
    localStorage.clear();
    this.router.navigate(['/login']);
  }

  irParaAgendamentos() {
    this.router.navigate(['/agendamentos']);
  }

  //PROCEDIMENTO
  carregarProcedimentos() {
    this.procedimentoService.listarProcedimentos().subscribe({
      next: (dados) => {
        this.procedimentos = dados;
        this.procedimentosOriginais = dados;
      },
      error: (err) => console.error('Erro ao carregar', err)
    });
  }
  
  filtrarProcedimentos(event: Event) {
    const termo = (event.target as HTMLInputElement).value.toLowerCase();

    if (!termo) {
      this.procedimentos = [...this.procedimentosOriginais];
      return;
    }

    this.procedimentos = this.procedimentosOriginais.filter(p => 
      p.nome.toLowerCase().includes(termo) || 
      p.descricao.toLowerCase().includes(termo)
    );
  }

  salvarProcedimento() {
    if (this.procedimentoForm.invalid) return;
    const dados = this.procedimentoForm.value;

    if (this.modoEdicao) {
      if (!this.procedimentoAtualId) {
        alert('O Back-end não retornou o ID do procedimento. Atualize o ProcedimentoResponseDTO no Java para permitir edições.');
        return;
      }
      this.procedimentoService.atualizarProcedimento(this.procedimentoAtualId, dados).subscribe({
        next: () => { this.carregarProcedimentos(); this.fecharModal(); },
        error: (err) => alert('Erro ao atualizar.')
      });
    } else {
      this.procedimentoService.criarProcedimento(dados).subscribe({
        next: () => { this.carregarProcedimentos(); this.fecharModal(); },
        error: (err) => alert('Erro ao criar.')
      });
    }
  }

  deletar(id: number) {
    if (!id) {
      alert('Erro: ID ausente. Atualize o DTO no Java.');
      return;
    }
    if (confirm('Excluir este procedimento?')) {
      this.procedimentoService.deletarProcedimento(id).subscribe({
        next: () => this.carregarProcedimentos(),
        error: (err) => console.error('Erro ao deletar', err)
      });
    }
  }

  //MODAL PROCEDIMENTO
  abrirModalNovo() {
    this.modoEdicao = false;
    this.procedimentoAtualId = null;
    this.procedimentoForm.reset();
    this.mostrarModal = true;
  }

  abrirModalEditar(procedimento: any) {
    this.modoEdicao = true;
    this.procedimentoAtualId = procedimento.id || null; 
    
    this.procedimentoForm.patchValue({
      nome: procedimento.nome,
      descricao: procedimento.descricao,
      observacaoDono: procedimento.observacaoDono || '',
      valor: procedimento.valor
    });
    this.mostrarModal = true;
  }

  fecharModal() {
    this.mostrarModal = false;
    this.procedimentoForm.reset();
  }

  //MODAL AGENDAMENTO
  abrirModalAgendamento(procedimento: any) {
  this.procedimentoSelecionado = procedimento;
  this.agendamentoForm.reset();
  this.mostrarModalAgendamento = true;
  }

  fecharModalAgendamento() {
    this.mostrarModalAgendamento = false;
    this.procedimentoSelecionado = null;
  }

  //AGENDAMENTO
  confirmarAgendamento() {
    if (this.agendamentoForm.invalid) return;
    const payload = {
      procedimento_id: this.procedimentoSelecionado.id,
      valorCobrado: this.procedimentoSelecionado.valor,
      nomeCliente: this.agendamentoForm.value.nomeCliente,
      email: this.agendamentoForm.value.email,
      data: this.agendamentoForm.value.data,
      horario: this.agendamentoForm.value.horario + ':00' 
    };

    this.agendamentoService.criarAgendamento(payload).subscribe({
      next: () => {
        alert('Agendamento solicitado com sucesso!');
        this.fecharModalAgendamento();
        this.router.navigate(['/agendamentos']);
      },
      error: (err) => {
        console.error(err);
        alert('Erro ao solicitar agendamento.');
      }
    });
  }
}