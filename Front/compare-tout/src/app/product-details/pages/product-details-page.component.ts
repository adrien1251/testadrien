import { Component, OnInit, OnDestroy, ɵConsole } from '@angular/core';
import { Product } from 'src/app/shared/models/product.interface';
import { Router } from '@angular/router';

@Component({
  selector: 'app-product-details-page',
  templateUrl: './product-details-page.component.html',
  styleUrls: ['./product-details-page.component.scss'],
})
export class ProductDetailsPageComponent implements OnInit, OnDestroy {

  product: Product;
  product2: Product;
  category: any;


  constructor(
    private router: Router,
  ) {
    this.product = this.router.getCurrentNavigation().extras.state.product;
    this.product2 = this.router.getCurrentNavigation().extras.state.product2;
    this.category = this.router.getCurrentNavigation().extras.state.cat;
  }

  ngOnInit(): void {
    this.product.criteriaDtoList.forEach(c => {
      if (c.criteriaUnit === 'null') { c.criteriaUnit = ''; }
    });
    this.seeLessDesc();
    this.seeLessTitle();

  }

  ngOnDestroy(): void {

  }


  seeMoreDesc() {
    this.product.descriptionShort = null;
  }

  seeLessDesc() {
    if (this.product.description.length > 150) {
      this.product.descriptionShort = this.product.description.slice(0, 150);
    }
  }

  seeMoreTitle() {
    this.product.nameShort = null;
  }

  seeLessTitle() {
    if (this.product.name.length > 25) {
      this.product.nameShort = this.product.name.slice(0, 25);
    }
  }

  goBack() {
    this.router.navigate(['/category', this.category.id], { state: { cat: this.category } } );
  }

  goToSupplierLink(product) {
    window.open(product.supplierLink, '_blank');
  }
}
