import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private http = inject(HttpClient)
  private apiUrl = 'http://localhost:8080/auth'

  login(credenciais: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/login`, credenciais).pipe(
      tap((response: any)=>{
        if(response.token)
          localStorage.setItem('token',response.token)
      })
    )
  }

  logout (){
    localStorage.removeItem('token')
  }
}
