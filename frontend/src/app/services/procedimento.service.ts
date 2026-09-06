import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class ProcedimentoService {
  private http = inject(HttpClient)
  private authService = inject(AuthService)
  private apiUrl = 'http://localhost:8080/procedimentos'

  private getHeaders ():{headers : HttpHeaders}{
    const token = this.authService.getToken()
    return {
      headers: new HttpHeaders({
        'Authorization': `Bearer ${token}`
      })
    }
  }

  listarProcedimentos(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl, this.getHeaders());
  }

  criarProcedimento(procedimento: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, procedimento, this.getHeaders());
  }

  atualizarProcedimento(id: number, procedimento: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}`, procedimento, this.getHeaders());
  }

  deletarProcedimento(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`, this.getHeaders());
  }
}
