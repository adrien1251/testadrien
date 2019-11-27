
import { Component, Input, OnInit } from '@angular/core';
import { Product } from 'src/app/shared/models/product.interface';
import { Router } from '@angular/router';

@Component({
    selector: 'app-product-display',
    templateUrl: './product-display.component.html',
    styleUrls: ['./product-display.component.scss'],
})
export class ProductDisplayComponent implements OnInit {

    @Input() product: Product;
    constructor(
        private router: Router,
    ) { }

    ngOnInit(): void {
    }

    goToDetails(): void {
        this.router.navigate(['/product', this.product.id], { state: { product: this.product } });
    }
}
