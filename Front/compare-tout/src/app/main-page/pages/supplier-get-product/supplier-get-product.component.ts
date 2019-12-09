import {Component, Input, OnInit} from '@angular/core';
import {AuthUtils} from '../../../shared/services/utils/auth-utils.service';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {SupplierService} from '../../../shared/services/supplier.service';
import {Supplier} from '../../../shared/models/supplier.interface';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-supplier-get-product',
  templateUrl: './supplier-get-product.component.html',
  styleUrls: ['./supplier-get-product.component.scss']
})
export class SupplierGetProductComponent implements OnInit {
  idSupplier: string = null;

  supplier: any = null;

  waiting = true;

  form: FormGroup = new FormGroup({
    firstName: new FormControl('',  [Validators.required]),
  });


  constructor(private supplierService: SupplierService, private route: ActivatedRoute) { }

  ngOnInit() {
    this.idSupplier = this.route.snapshot.paramMap.get('id');
    if (this.idSupplier !== null) {
      this.supplierService.getSupplier(this.idSupplier).subscribe((supplier: Supplier) => {
        this.supplier = supplier;
        console.log(this.supplier)
        this.form = new FormGroup({
          firstName: new FormControl(this.supplier.firstName),
          lastName: new FormControl(this.supplier.lastName),
          email: new FormControl(this.supplier.email),
          webSite: new FormControl(this.supplier.webSite),
          siret: new FormControl(this.supplier.siret),
        });
        this.form.disable;
        this.waiting = false;
      },
        () => {
          this.waiting = false;
        });
    } else {
      this.supplier = AuthUtils.getCurrentUser().user;
      this.form = new FormGroup({
        firstName: new FormControl(this.supplier.firstName,  [Validators.required]),
        lastName: new FormControl(this.supplier.lastName,  [Validators.required]),
        email: new FormControl(this.supplier.email,  [Validators.required, Validators.email]),
        webSite: new FormControl(this.supplier.webSite,  [Validators.required]),
        siret: new FormControl(this.supplier.siret,  [Validators.required]),
      });

      this.waiting = false;
    }
  }

  submit() {
    if (this.form.valid && this.idSupplier === null) {
      return;
    }
  }

}
