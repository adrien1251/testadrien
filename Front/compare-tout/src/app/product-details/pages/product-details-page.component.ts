import { Component, OnInit, OnDestroy, ɵConsole } from '@angular/core';
import { CategoryService } from 'src/app/shared/services/category.service';
import { CriteriaService } from 'src/app/shared/services/criteria.service';
import { Product } from 'src/app/shared/models/product.interface';
import { ProductService } from 'src/app/shared/services/product.service';
import { Router, Route, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-product-details-page',
  templateUrl: './product-details-page.component.html',
  styleUrls: ['./product-details-page.component.scss'],
})
export class ProductDetailsPageComponent implements OnInit, OnDestroy {

  product: Product;
  test: any;


  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private categoryService: CategoryService,
    private criteriaService: CriteriaService,
    private productService: ProductService,
  ) {
    this.product = this.router.getCurrentNavigation().extras.state.product;
  }

  ngOnInit(): void {
    console.log(this.product);
    this.product.criteriaDtoList.forEach(c => {
      if (c.criteriaUnit === 'null') { c.criteriaUnit = ''; }
    });
    this.seeLessDesc();

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
}
