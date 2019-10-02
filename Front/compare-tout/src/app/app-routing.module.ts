import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { TestComponent } from './test/test.component';


const routes: Routes = [
  // C'est ici que l'on définit les routes
  // @Path définit ce que va être l'url ex /test donne localhost:4200/test
  // @component précise le nom du component à afficher ex TestComponent
  {
    path: 'test',
    component: TestComponent,
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
