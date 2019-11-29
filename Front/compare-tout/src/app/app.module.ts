import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HttpClientModule} from '@angular/common/http';
import {TestService} from './shared/services/test.service';
import {SearchResultModule} from './search-result/search-result.module';
import {MainPageComponent} from './main-page/pages/main-page.component';
import {HeaderComponent} from './shared/header/header.component';
import {CategoryService} from './shared/services/category.service';
import {MainPageModule} from './main-page/main-page.module';
import {MenuCategoriesComponent} from './main-page/components/menu-categories/menu-categories.component';
import {FiltersComponent} from './main-page/components/filters/filters.component';
import {FiltersValueComponent} from './main-page/components/filters-value/filters-value.component';
import {CriteriaService} from './shared/services/criteria.service';
import {ProductService} from './shared/services/product.service';
import {ProductDisplayComponent} from './main-page/components/product-display/product-display.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {LoginComponent} from './Auth/pages/login/login.component';

import {CommonModule} from '@angular/common';
import {
  MatButtonModule,
  MatCardModule,
  MatDatepickerModule,
  MatDialogModule,
  MatDividerModule,
  MatIconModule,
  MatInputModule,
  MatListModule,
  MatMenuModule,
  MatNativeDateModule,
  MatOptionModule,
  MatProgressSpinnerModule,
  MatSelectModule,
  MatSidenavModule,
  MatSliderModule,
  MatTableModule,
  MatTabsModule,
  MatToolbarModule
} from '@angular/material';
import {ReactiveFormsModule} from '@angular/forms';
import {AuthUtils} from './shared/services/utils/auth-utils.service';
import { FiltersSliderComponent } from './main-page/components/filters-slider/filters-slider.component';
import { Ng5SliderModule } from 'ng5-slider';
import { ProductDetailsPageComponent } from './product-details/pages/product-details-page.component';
import {AuthComponent} from './Auth/pages/auth/auth.component';
import {InscriptionFournComponent} from './Auth/pages/inscription-fourn/inscription-fourn.component';
import {InscriptionCustomerComponent} from './Auth/pages/inscription-customer/inscription-customer.component';
import {AdminComponent} from './main-page/pages/admin/admin.component';

@NgModule({
  declarations: [
    AppComponent,
    AdminComponent,
    MainPageComponent,
    ProductDetailsPageComponent,
    HeaderComponent,
    MenuCategoriesComponent,
    FiltersComponent,
    FiltersValueComponent,
    ProductDisplayComponent,
    LoginComponent,
    FiltersSliderComponent,
    AuthComponent,
    InscriptionFournComponent,
    InscriptionCustomerComponent,
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
    MatMenuModule,
    MatIconModule,
    MatSidenavModule,
    MatProgressSpinnerModule,
    MatDividerModule,
    MatListModule,
    MatSliderModule,
    Ng5SliderModule,
    MatTabsModule,
    MatOptionModule,
    MatSelectModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatTableModule,
  ],
  exports: [
    MainPageComponent,
    ProductDetailsPageComponent,
    CommonModule,
    MatToolbarModule,
    MatButtonModule,
    MatCardModule,
    MatInputModule,
    MatDialogModule,
    MatMenuModule,
    MatIconModule,
    MatProgressSpinnerModule,
  ],
  providers: [
    TestService,
    CategoryService,
    CriteriaService,
    ProductService,
    AuthUtils,
    MatDatepickerModule,
    MatTableModule
  ],
  entryComponents: [
    AuthComponent
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
