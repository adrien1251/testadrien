import { NgModule } from '@angular/core';
import { SearchResultPageComponent } from './pages/search-result-page.component';
import { SearchBarComponent } from './components/search-bar/search-bar.component';
import { SearchResultRoutingModule } from './search-result-routing.module';
import { CommonModule } from '@angular/common';
import { MatCheckboxModule } from '@angular/material/checkbox';


@NgModule({
  imports: [
    SearchResultRoutingModule,
    CommonModule,
    MatCheckboxModule,
  ],
  declarations: [
    SearchResultPageComponent,
    SearchBarComponent,
  ],
  exports: [
    SearchResultRoutingModule,
    MatCheckboxModule,
  ],
})

export class SearchResultModule { }
