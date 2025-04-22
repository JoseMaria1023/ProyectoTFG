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

export const appRoutes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'login', component: LoginComponent },
  { path: 'Crear-gira', component: CrearGiraComponent },
  { path: 'Crear-artista', component: CrearArtistaComponent },
  { path: 'Crear-recinto', component: CrearRecintoComponent },
  { path: 'Editar-recinto', component: EditarRecintoComponent },
  { path: 'Gestionar-recinto', component: GestionarRecintosComponent},
  { path: 'Crear-zona', component: CrearZonaComponent },
  { path: 'Editar-zona', component: EditarZonaComponent },
  { path: 'Gestionar-zona', component: GestionarZonaComponent },
  { path: 'Crear-concierto', component: CrearConciertoComponent},
  { path: 'Editar-concierto', component: EditarConciertoComponent},
  { path: 'Gestionar-concierto', component: GestionarConciertosComponent},
  { path: 'Crear-asiento', component: CrearAsientoComponent},
  { path: 'Editar-asiento', component: EditarAsientoComponent},
  { path: 'Gestionar-asiento', component: GestionarAsientosComponent},
  { path: 'Comprar-entrada', component: ComprarEntradaComponent},
  { path: 'Elegir-concierto', component: ElegirConciertoComponent},
  { path: 'Gestionar-artista', component: GestionarArtistaComponent},
  { path: 'Gestionar-gira', component: GestionarGiraComponent},
  { path: 'Editar-artista', component: EditarArtistaComponent},
  { path: 'Editar-gira', component: EditarGiraComponent},
  { path: 'mi-perfil', component: MiPerfilComponent },
  { path: 'Gestionar-asiento-artista', component: GestionarAsientoConciertoArtistaComponent },




  { path: '', redirectTo: '/home', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(appRoutes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
