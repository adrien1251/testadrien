import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MainPageComponent } from './main-page/pages/main-page.component';
import { ProductDetailsPageComponent } from './product-details/pages/product-details-page.component';
import {AdminComponent} from './main-page/pages/admin/admin.component';
import {AuthGuardAdminService} from './shared/services/routeGuard/auth-guard.-admin.service';
import {SupplierSendProductComponent} from './main-page/pages/supplier-send-product/supplier-send-product.component';
import {SupplierGetProductComponent} from './main-page/pages/supplier-get-product/supplier-get-product.component';


const routes: Routes = [
  // {path: '', component: MainPageComponent},
  {
    path: '',
    redirectTo: 'category/all',
    pathMatch: 'full',
  },
  {
    path: 'category/:id',
    component: MainPageComponent,
  },
  {
    path: 'category/all',
    component: MainPageComponent,
  },
  {
    path: 'product/:id/:id2',
    component: ProductDetailsPageComponent,
  },
  {
    path: 'product/:id',
    component: ProductDetailsPageComponent,
  },
  {
    path: 'admin/supplier',
    component: AdminComponent,
    canActivate: [AuthGuardAdminService],
    data: {
      expectedRole: 'admin'
    }
  },
  {
    path: 'supplier/me',
    component: SupplierGetProductComponent,
    canActivate: [AuthGuardAdminService],
    data: {
      expectedRole: 'supplier'
    }
  },
  {
    path: 'supplier/:id',
    component: SupplierGetProductComponent,
  },
  {
    path: 'supplier/sendProduct',
    component: SupplierSendProductComponent,
    canActivate: [AuthGuardAdminService],
    data: {
      expectedRole: 'supplier'
    }
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  declarations: [],
  providers: [AuthGuardAdminService]
})
export class AppRoutingModule { }
