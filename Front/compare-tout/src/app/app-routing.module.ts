import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MainPageComponent } from './main-page/pages/main-page.component';
import { ProductDetailsPageComponent } from './product-details/pages/product-details-page.component';


const routes: Routes = [
  // C'est ici que l'on définit les routes
  // @Path définit ce que va être l'url ex /test donne localhost:4200/test
  // @component précise le nom du component à afficher ex TestComponent
  {
    path: 'category/:id',
    component: MainPageComponent,
  },
  {
    path: '',
    component: MainPageComponent,
  },
  {
    path: 'product/:id',
    component: ProductDetailsPageComponent,
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  declarations: [],
})
export class AppRoutingModule { }
