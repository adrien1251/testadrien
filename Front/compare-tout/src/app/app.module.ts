import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { TestService } from './shared/services/test.service';
import { SearchResultModule } from './search-result/search-result.module';
import { MainPageComponent } from './main-page/pages/main-page.component';
import { HeaderComponent } from './shared/header/header.component';
import { CategoryService } from './shared/services/category.service';
import { MainPageModule } from './main-page/main-page.module';
import { MenuCategoriesComponent } from './main-page/components/menu-categories/menu-categories.component';
import { FiltersComponent } from './main-page/components/filters/filters.component';
import { FiltersValueComponent } from './main-page/components/filters-value/filters-value.component';
import { CriteriaService } from './shared/services/criteria.service';
import { ProductDisplayComponent } from './main-page/components/product-display/product-display.component';

@NgModule({
  declarations: [
    AppComponent,
    MainPageComponent,
    HeaderComponent,
    MenuCategoriesComponent,
    FiltersComponent,
    FiltersValueComponent,
    ProductDisplayComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    SearchResultModule,
    MainPageModule,
  ],
  exports: [
    MainPageComponent,
  ],
  providers: [
    TestService,
    CategoryService,
    CriteriaService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
