import { Component, OnInit, OnDestroy, ɵConsole, Input } from '@angular/core';
import { Product } from 'src/app/shared/models/product.interface';
import { Router } from '@angular/router';

@Component({
  selector: 'app-product-compare',
  templateUrl: './product-compare.component.html',
  styleUrls: ['./product-compare.component.scss'],
})
export class ProductCompareComponent implements OnInit, OnDestroy {

  @Input()product: Product;
  @Input()product2: Product;
  category: any;


  constructor(
    private router: Router,
  ) {
  }

  ngOnInit(): void {
    this.product.criteriaDtoList.forEach(c => {
      if (c.criteriaUnit === 'null') { c.criteriaUnit = ''; }
    });
    this.product2.criteriaDtoList.forEach(c => {
        if (c.criteriaUnit === 'null') { c.criteriaUnit = ''; }
      });
    this.seeLessTitle();

  }

  ngOnDestroy(): void {

  }

  seeMoreTitle() {
    this.product.nameShort = null;
    this.product2.nameShort = null;
  }

  seeLessTitle() {
    if (this.product.name.length > 15) {
      this.product.nameShort = this.product.name.slice(0, 15);
    }
    if (this.product2.name.length > 15) {
        this.product.nameShort = this.product.name.slice(0, 15);
      }
  }

  goBack() {
    this.router.navigate(['/category', this.category.id], { state: { cat: this.category } } );
  }
}
