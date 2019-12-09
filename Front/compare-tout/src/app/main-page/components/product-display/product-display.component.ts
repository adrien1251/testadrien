
import { Component, Input, OnInit, Output, EventEmitter, OnChanges } from '@angular/core';
import { Product } from 'src/app/shared/models/product.interface';
import { Router } from '@angular/router';

@Component({
    selector: 'app-product-display',
    templateUrl: './product-display.component.html',
    styleUrls: ['./product-display.component.scss'],
})
export class ProductDisplayComponent implements OnInit, OnChanges {

    @Input() product: Product;
    @Input() category: any;
    @Input() hasReachedComparison: number;
    @Output() compareProduct: EventEmitter<any> = new EventEmitter();

    constructor(
        private router: Router,
    ) { }

    ngOnInit(): void {
        // this.product.selected = this.product.selected != null ? !this.product.selected : true;

    }

    ngOnChanges(): void {
        // this.product.selected = this.product.selected != null ? !this.product.selected : true;

    }


    goToDetails(): void {
        this.router.navigate(['/product', this.product.id], { state: { product: this.product, cat: this.category} });
    }

    updateComparison(event) {
        console.log(this.product);
        this.compareProduct.emit(event);
        this.product.selected = this.product.selected != null ? !this.product.selected : true;

    }
}
