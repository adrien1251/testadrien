import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SearchResultPageComponent } from './pages/search-result-page.component';

const routes: Routes = [
  {
    path: 'search-results',
    component: SearchResultPageComponent,
  },
];

@NgModule({
  imports: [
    RouterModule.forChild(routes),
  ],
  exports: [
    RouterModule,
  ],
})
export class SearchResultRoutingModule { }
