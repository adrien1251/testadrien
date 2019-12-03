import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Product } from 'src/app/shared/models/product.interface';
import { Router } from '@angular/router';

@Component({
    selector: 'app-comparison-popup',
    templateUrl: './comparison-popup.component.html',
    styleUrls: ['./comparison-popup.component.scss'],
})
export class ComparisonPopupComponent {

    form: FormGroup;
    products: Product[];
    category: any;

    constructor(
        private formBuilder: FormBuilder,
        private dialogRef: MatDialogRef<ComparisonPopupComponent>,
        @Inject(MAT_DIALOG_DATA) data,
        private router: Router,
        // private checkinService: CheckinService,
    ) {
        // this.currentUser = data.currentUser;
        this.form = this.formBuilder.group({});
        this.products = data.products;
        this.category = data.category;
    }

    isFormValid(): boolean {
        return this.form.valid;
    }

    doCompare() {
        // console.log(this.products);
        this.router.navigate(['/product', this.products[0].id, this.products[1].id],
            { state: { product: this.products[0], product2: this.products[1], cat: this.category } });
        this.dialogRef.close();

    }

    cancel() {
        this.dialogRef.close();
    }


    submit(): void {

    }

}
