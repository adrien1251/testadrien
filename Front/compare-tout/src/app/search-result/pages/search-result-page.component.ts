import { Component, OnInit, OnDestroy } from '@angular/core';
import { Product } from 'src/app/shared/models/product.interface';
import { Criteria } from 'src/app/shared/models/criteria.interface';
import { productMock } from 'src/app/shared/mocks/product-mock';
import { filter } from 'minimatch';

@Component({
  selector: 'app-search-result-page',
  templateUrl: './search-result-page.component.html',
  styleUrls: ['./search-result-page.component.scss'],
})
export class SearchResultPageComponent implements OnInit, OnDestroy {
  public productList: Product[];
  public criteriaList: Criteria[];

  constructor(
  ) { }

  ngOnInit(): void {
      this.fetchProducts();
  }

  ngOnDestroy(): void {

  }

  fetchProducts(): void {
    this.productList = productMock;
    this.fetchCriterias();
  }

  fetchCriterias(): void {
    this.criteriaList = [];
    this.productList.forEach(product => {
        product.criteriaList.forEach(crit => {
          this.criteriaList.push(crit);
        });
    });
  }

  reloadProducts(event): void {
    if (event.checked) {
      const filteredProducts = [];
      this.productList.forEach(p => {
        p.criteriaList.forEach( crit => {
          if (crit.value === event.value) {
            filteredProducts.push(p);
          }
        });
      });
      this.productList = filteredProducts;
    } else {
      this.fetchProducts();
    }

  }

}
