import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MenuCategoriesComponent } from './components/menu-categories/menu-categories.component';
import { AdminComponent } from './pages/admin/admin.component';


@NgModule({
  imports: [
    CommonModule,
    MatCheckboxModule,
  ],
  declarations: [
    // MenuCategoriesComponent,
  AdminComponent],
  exports: [
    MatCheckboxModule,
  ],
})

export class MainPageModule { }
