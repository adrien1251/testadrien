import { Component, OnInit, OnDestroy } from '@angular/core';
import { Category } from 'src/app/shared/models/category.interface';
import { CategoryService } from 'src/app/shared/services/category.service';
import { categoryMock1, categoryMock2 } from 'src/app/shared/mocks/category-mock';
import { criteriaMock1, criteriaMock2 } from 'src/app/shared/mocks/critere-mock';
import { CriteriaService } from 'src/app/shared/services/criteria.service';
import { Product } from 'src/app/shared/models/product.interface';
import { productMock } from 'src/app/shared/mocks/product-mock';

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.scss'],
})
export class MainPageComponent implements OnInit, OnDestroy {
  public categories: Category[];
  public currentCategory: Category;
   public subCategories: Category[];
  public isChildCategory: boolean;
  public productList: Product[];


  constructor(
    private categoryService: CategoryService,
    private criteriaService: CriteriaService,
  ) { }

  ngOnInit(): void {
    this.fetchCategories();
  }

  ngOnDestroy(): void {

  }

  fetchCategories(): void {
    this.categoryService.getCategories().subscribe(res => {
      if (res != null && res.length !== 0) {
        this.categories = res;
      }
    }
    );
  }

  fetchCurrentCategory(event): void {
    if (event != null) {
      this.categoryService.getCategoriesChild(event.id).subscribe((res) => {
        this.isChildCategory = res.length === 0;
        this.subCategories = res;
      });
      this.currentCategory = event;
    } else {
      this.currentCategory = null;
    }
    this.criteriaService.getCriterias(this.currentCategory.id).subscribe((res) => {
      this.currentCategory.criteriaList = res;
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
      this.productList = productMock;
    }

  }

}
