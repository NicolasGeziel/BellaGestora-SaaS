import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class AgendamentoService {
  private http = inject(HttpClient);
  private authService = inject(AuthService);
  private apiUrl = 'http://localhost:8080/agendamentos';

  private getHeaders() {
    return { headers: new HttpHeaders({ 'Authorization': `Bearer ${this.authService.getToken()}` }) };
  }

  listarAgendamentos(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl, this.getHeaders());
  }

  criarAgendamento(dados: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, dados, this.getHeaders());
  }

  atualizarAgendamento(id: number, dados: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}`, dados, this.getHeaders());
  }

  deletarAgendamento(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${id}`, this.getHeaders());
  }

  mudarStatus(id: number, status: string): Observable<any> {
    return this.http.patch<any>(`${this.apiUrl}/${id}/status`, { status }, this.getHeaders());
  }
}