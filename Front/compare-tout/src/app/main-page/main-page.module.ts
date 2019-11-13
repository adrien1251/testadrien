import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MenuCategoriesComponent } from './components/menu-categories/menu-categories.component';


@NgModule({
  imports: [
    CommonModule,
    MatCheckboxModule,
  ],
  declarations: [
    // MenuCategoriesComponent,
  ],
  exports: [
    MatCheckboxModule,
  ],
})

export class MainPageModule { }
