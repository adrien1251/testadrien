import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Error} from '../../../shared/models/error.interface';
import {SupplierService} from '../../../shared/services/supplier.service';
// import {SupplierService} from '../../../shared/services/supplier.service';

@Component({
  selector: 'app-supplier-send-product',
  templateUrl: './supplier-send-product.component.html',
  styleUrls: ['./supplier-send-product.component.scss']
})
export class SupplierSendProductComponent implements OnInit {
  error: string | null;
  isWaiting = false;

  form: FormGroup = new FormGroup({
    file: new FormControl('',  [Validators.required]),
  });

  constructor(private supplierService: SupplierService) { }

  ngOnInit() {
  }

  submit() {
  //   if (this.form.valid) {
  //     this.isWaiting = true;
  //     const formData = new FormData();
  //     formData.append('file', this.form.value.file);
  //     console.log(this.form.value.file);
  //     this.supplierService.sendProducts(formData).subscribe(
  //       (user: any) => {
  //         this.error = "ok";
  //       },
  //       (error: Error) => {
  //         if (error.error.statusErrorCode === 404) {
  //           this.error = error.error.errorMessage;
  //         } else if (error.error.statusErrorCode === 403) {
  //           this.error = error.error.errorMessage;
  //         } else {
  //           this.error = "Une erreur innatendu s'est produite";
  //         }
  //         this.isWaiting = false;
  //       },
  //       () => {
  //         this.isWaiting = false;
  //       }
  //     );
  //   } else {
  //     this.error = "L'un des champs n'est pas remplit";
  //   }
  }
}
