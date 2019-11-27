import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MainPageComponent } from './main-page/pages/main-page.component';
import {AdminComponent} from './main-page/pages/admin/admin.component';
import {AuthGuardAdminService} from './shared/services/routeGuard/auth-guard.-admin.service';


const routes: Routes = [
  // C'est ici que l'on définit les routes
  // @Path définit ce que va être l'url ex /test donne localhost:4200/test
  // @component précise le nom du component à afficher ex TestComponent
  {path: '', component: MainPageComponent},
  {
    path: 'admin/supplier',
    component: AdminComponent,
    canActivate: [AuthGuardAdminService],
    data: {
      expectedRole: 'admin'
    }
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  declarations: [],
  providers: [AuthGuardAdminService]
})
export class AppRoutingModule { }
