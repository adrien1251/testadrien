import { NgModule } from '@angular/core';
import { SearchResultPageComponent } from './pages/search-result-page.component';
import { FiltersComponent } from './components/filters/filters.component';
import { ProductListComponent } from './components/product-list/product-list.component';
import { SearchBarComponent } from './components/search-bar/search-bar.component';
import { SearchResultRoutingModule } from './search-result-routing.module';
import { ProductBoxComponent } from './components/product-box/product-box.component';
import { FiltersValueComponent } from './components/filters-value/filters-value.component';
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
    FiltersComponent,
    ProductListComponent,
    SearchBarComponent,
    ProductBoxComponent,
    FiltersValueComponent,
  ],
  exports: [
    SearchResultRoutingModule,
    MatCheckboxModule,
  ],
})

export class SearchResultModule { }
