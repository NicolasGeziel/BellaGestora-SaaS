import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { AgendamentosComponent } from './pages/agendamentos/agendamentos.component';
import { ProcedimentosComponent } from './pages/procedimentos/procedimentos.component';
import { RegistroComponent } from './pages/registro/registro.component';

export const routes: Routes = [
    { path: '', redirectTo: '/login', pathMatch: 'full' },
    { path: 'login', component: LoginComponent },
    { path:'agendamentos', component: AgendamentosComponent },
    { path:'procedimentos', component: ProcedimentosComponent },
    { path: 'registro', component: RegistroComponent }
];
