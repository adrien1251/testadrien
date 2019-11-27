import { Component, OnInit, OnDestroy, ɵConsole } from '@angular/core';
import { CategoryService } from 'src/app/shared/services/category.service';
import { CriteriaService } from 'src/app/shared/services/criteria.service';
import { Product } from 'src/app/shared/models/product.interface';
import { ProductService } from 'src/app/shared/services/product.service';
import { Router, Route, ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

@Component({
  selector: 'app-product-details-page',
  templateUrl: './product-details-page.component.html',
  styleUrls: ['./product-details-page.component.scss'],
})
export class ProductDetailsPageComponent implements OnInit, OnDestroy {

  product: Product;
  category: any;


  constructor(
    private router: Router,
    private location: Location,
  ) {
    this.product = this.router.getCurrentNavigation().extras.state.product;
    this.category = this.router.getCurrentNavigation().extras.state.cat;
  }

  ngOnInit(): void {
    console.log(this.product);
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
}
