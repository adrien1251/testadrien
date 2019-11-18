
import { Component, Input, OnInit } from '@angular/core';
import { Product } from 'src/app/shared/models/product.interface';

@Component({
    selector: 'app-product-display',
    templateUrl: './product-display.component.html',
    styleUrls: ['./product-display.component.scss'],
})
export class ProductDisplayComponent implements OnInit {

    @Input() product: Product;
    constructor() { }

    ngOnInit(): void {
        // .
    }
}
