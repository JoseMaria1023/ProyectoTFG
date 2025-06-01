import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { CrearArtistaComponent } from './crear-artista/crear-artista.component';
import { CrearRecintoComponent } from './crear-recinto/crear-recinto.component';
import { CrearZonaComponent } from './crear-zona/crear-zona.component';
import { CrearConciertoComponent } from './crear-concierto/crear-concierto.component';
import { CrearAsientoComponent } from './crear-asiento/crear-asiento.component';
import { ComprarEntradaComponent } from './comprar-entrada/comprar-entrada.component';
import { ElegirConciertoComponent } from './elegir-concierto/elegir-concierto.component';
import { GestionarArtistaComponent } from './gestionar-artista/gestionar-artista.component';
import { EditarArtistaComponent } from './editar-artista/editar-artista.component';
import { EditarGiraComponent } from './editar-gira/editar-gira.component';
import { GestionarGiraComponent } from './gestionar-gira/gestionar-gira.component';
import { CrearGiraComponent } from './crear-gira/crear-gira.component';
import { EditarRecintoComponent } from './editar-recinto/editar-recinto.component';
import { GestionarRecintosComponent } from './gestionar-recinto/gestionar-recinto.component';
import { EditarZonaComponent } from './editar-zona/editar-zona.component';
import { GestionarZonaComponent } from './gestionar-zona/gestionar-zona.component';
import { EditarConciertoComponent } from './editar-concierto/editar-concierto.component';
import { GestionarConciertosComponent } from './gestionar-concierto/gestionar-concierto.component';
import { EditarAsientoComponent } from './editar-asiento/editar-asiento.component';
import { GestionarAsientosComponent } from './gestionar-asiento/gestionar-asiento.component';
import { GestionarAsientoConciertoArtistaComponent } from './gestionar-asiento-concierto-artista/gestionar-asiento-concierto-artista.component';
import { MiPerfilComponent } from './mi-perfil/mi-perfil.component';
import { AdminGuard } from './Guards/admin.guard'; 
import { UserGuard } from './Guards/user.guard';
import { ArtistaGuard } from './Guards/artista.guard';
import { CrearRecintoConZonasComponent } from './crear-recinto-con-zonas/crear-recinto-con-zonas.component';
import { ListaArtistaComponent } from './lista-artista/lista-artista.component';
import { MapaAsientosComponent } from './mapa-asientos/mapa-asientos.component';

export const appRoutes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'login', component: LoginComponent },
  { path: 'Crear-gira', component: CrearGiraComponent,canActivate: [AdminGuard] },
  { path: 'Crear-artista', component: CrearArtistaComponent,canActivate: [AdminGuard] },
  { path: 'Crear-recinto', component: CrearRecintoComponent ,canActivate: [AdminGuard]},
  { path: 'Editar-recinto', component: EditarRecintoComponent,canActivate: [AdminGuard] },
  { path: 'Gestionar-recinto', component: GestionarRecintosComponent,canActivate: [AdminGuard]},
  { path: 'Crear-zona', component: CrearZonaComponent,canActivate: [AdminGuard] },
  { path: 'Editar-zona', component: EditarZonaComponent,canActivate: [AdminGuard] },
  { path: 'Gestionar-zona', component: GestionarZonaComponent,canActivate: [AdminGuard] },
  { path: 'Gestionar-zona', component: GestionarZonaComponent,canActivate: [AdminGuard] },
  { path: 'Crear-zonas-recintos', component: CrearRecintoConZonasComponent,canActivate: [AdminGuard] },
  { path: 'Crear-concierto', component: CrearConciertoComponent,canActivate: [AdminGuard]},
  { path: 'Editar-concierto', component: EditarConciertoComponent,canActivate: [AdminGuard]},
  { path: 'Gestionar-concierto', component: GestionarConciertosComponent,canActivate: [AdminGuard]},
  { path: 'Crear-asiento', component: CrearAsientoComponent,canActivate: [AdminGuard]},
  { path: 'Editar-asiento', component: EditarAsientoComponent,canActivate: [AdminGuard]},
  { path: 'Gestionar-asiento', component: GestionarAsientosComponent,canActivate: [AdminGuard]},
  { path: 'Comprar-entrada', component: ComprarEntradaComponent},
  { path: 'Elegir-concierto', component: ElegirConciertoComponent},
  { path: 'Gestionar-artista', component: GestionarArtistaComponent,canActivate: [AdminGuard]},
  { path: 'Gestionar-gira', component: GestionarGiraComponent,canActivate: [AdminGuard]},
  { path: 'Editar-artista', component: EditarArtistaComponent,canActivate: [AdminGuard]},
  { path: 'Listar-artista', component: ListaArtistaComponent},
  { path: 'Editar-gira', component: EditarGiraComponent,canActivate: [AdminGuard]},
  { path: 'mi-perfil', component: MiPerfilComponent },
  { path: 'mapa-asientos/:id', component: MapaAsientosComponent },
  { path: 'Gestionar-asiento-artista', component: GestionarAsientoConciertoArtistaComponent,canActivate: [ArtistaGuard] },




  { path: '', redirectTo: '/home', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(appRoutes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
