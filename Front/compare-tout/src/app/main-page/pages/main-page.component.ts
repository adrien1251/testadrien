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
  private currentCategory: Category;
  private subCategories: Category[];
  private isChildCategory: boolean;
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
    // this.categoryService.getCategories().subscribe(res => {
    //   if (res != null && res.length !== 0) {
    //     this.categories = res;
    //   } else {
    //     this.categories = [categoryMock1];
    //     this.categories.push(categoryMock2);
    //   }
    // }
    // );
    this.categories = [categoryMock1];
    this.categories.push(categoryMock2);
    this.productList = productMock;
  }

  fetchCurrentCategory(event): void {
    this.isChildCategory = event.childList == null;
    if (event != null) {
      this.categoryService.getCategories(event.id).subscribe((res) => {
        this.subCategories = res;
      });
      this.subCategories = event.childList;
      this.currentCategory = event;
      this.currentCategory.criteriaList = criteriaMock1.concat(criteriaMock2);
      // this.currentCategory.criteriaList.push(criteriaMock2);
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
