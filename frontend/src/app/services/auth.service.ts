import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { jwtDecode } from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8080/auth'; 

  login(credenciais: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/login`, credenciais).pipe(
      tap((response: any) => {
        if (response.token) {
          localStorage.setItem('token', response.token);
        }
      })
    );
  }

  logout() {
    localStorage.clear();
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  isAdmin(): boolean {
    const token = this.getToken();
    if (!token) return false;

    try {
      const decoded: any = jwtDecode(token);
      const claim = decoded.role

      if (Array.isArray(claim)) {
        return claim.includes('ADMIN') || claim.includes('ROLE_ADMIN');
      }
      
      return claim === 'ADMIN' || claim === 'ROLE_ADMIN';

    } catch (error) {
      console.error('Falha ao ler o token', error);
      return false;
    }
  }

  isUser(): boolean {
    return !this.isAdmin();
  }
}