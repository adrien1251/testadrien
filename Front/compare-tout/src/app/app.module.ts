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
import {ProductDisplayComponent} from './main-page/components/product-display/product-display.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LoginComponent } from './Auth/pages/login/login.component';

import { CommonModule } from '@angular/common';
import {
  MatButtonModule, MatCardModule, MatDialogModule, MatInputModule, MatTableModule,
  MatToolbarModule, MatMenuModule, MatIconModule, MatProgressSpinnerModule, MatSidenavModule,
  MatDividerModule, MatListModule, MatTabsModule
} from '@angular/material';
import {ReactiveFormsModule} from '@angular/forms';
import {AuthUtils} from './shared/services/utils/auth-utils.service';
import { AuthComponent } from './Auth/pages/auth/auth.component';
import { InscriptionFournComponent } from './Auth/pages/inscription-fourn/inscription-fourn.component';

@NgModule({
  declarations: [
    AppComponent,
    MainPageComponent,
    HeaderComponent,
    MenuCategoriesComponent,
    FiltersComponent,
    FiltersValueComponent,
    ProductDisplayComponent,
    LoginComponent,
    AuthComponent,
    InscriptionFournComponent,
  ],
  imports: [
    BrowserModule,
    ReactiveFormsModule,
    AppRoutingModule,
    HttpClientModule,
    SearchResultModule,
    MainPageModule,
    BrowserAnimationsModule,
    CommonModule,
    MatToolbarModule,
    MatButtonModule,
    MatCardModule,
    MatInputModule,
    MatDialogModule,
    MatTableModule,
    MatMenuModule,
    MatIconModule,
    MatSidenavModule,
    MatProgressSpinnerModule,
    MatDividerModule,
    MatTabsModule,
    MatListModule
  ],
  exports: [
    MainPageComponent,
    CommonModule,
    MatToolbarModule,
    MatButtonModule,
    MatCardModule,
    MatInputModule,
    MatDialogModule,
    MatTableModule,
    MatMenuModule,
    MatIconModule,
    MatProgressSpinnerModule,
  ],
  providers: [
    TestService,
    CategoryService,
    CriteriaService,
    AuthUtils
  ],
  entryComponents: [
    AuthComponent
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
