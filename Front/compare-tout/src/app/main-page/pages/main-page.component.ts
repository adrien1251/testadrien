import { Component, OnInit, OnDestroy, ɵConsole } from '@angular/core';
import { Category } from 'src/app/shared/models/category.interface';
import { CategoryService } from 'src/app/shared/services/category.service';
import { categoryMock1, categoryMock2 } from 'src/app/shared/mocks/category-mock';
import { criteriaMock1, criteriaMock2 } from 'src/app/shared/mocks/critere-mock';
import { CriteriaService } from 'src/app/shared/services/criteria.service';
import { Product } from 'src/app/shared/models/product.interface';
import { productMock } from 'src/app/shared/mocks/product-mock';
import { ProductService } from 'src/app/shared/services/product.service';
import { Criteria, UniqueCriteria } from 'src/app/shared/models/criteria.interface';

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
  public criteriaList: Criteria[] = [];
  public criteriaValues: UniqueCriteria[] = [];
  canShowFilters = false;


  constructor(
    private categoryService: CategoryService,
    private criteriaService: CriteriaService,
    private productService: ProductService,
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
      if (this.currentCategory) {
        this.criteriaService.getCriterias(this.currentCategory.id).subscribe((res) => {
          this.currentCategory.criteriaList = res;
          // this.criteriaList = res;
          this.fetchProducts();
        });
      }
    } else {
      this.currentCategory = null;
    }

  }

  fetchProducts(): void {
    this.productService.getProductsByCategoryAndCriteria(this.currentCategory.id, null).subscribe((res) => {
      this.productList = res;
      this.productList.forEach(product => {
        this.productService.getCriteriasOfProduct(product.id).subscribe(criterias => {
          criterias.forEach(criteria => {
            const alreadyIn = this.criteriaValues.find(c => c.id === criteria.id) != null;
            if (!alreadyIn) {
              const newCrit: UniqueCriteria = {
                id: criteria.id,
                name: criteria.criteriaName,
                type: criteria.type,
                unit: criteria.criteriaUnit,
                values: [criteria.value]
              };
              this.criteriaValues.push(newCrit);
            } else {
              const idx = this.criteriaValues.findIndex(c => c.id === criteria.id);
              if (idx !== -1) {
                if (this.criteriaValues[idx].values.find(v => v === criteria.value) == null) {
                  this.criteriaValues[idx].values.push(criteria.value);
                }
              }
            }
          });
          this.canShowFilters = true;
        });
      });
    });
  }

  reloadProducts(event): void {
    console.log(event);
  }

  sendCriterias(event): void {
    if (event == null || event.length === 0) { event = null; }
    this.productService.getProductsByCategoryAndCriteria(this.currentCategory.id, event).subscribe( res => {
      this.productList = res;
    }
    );
  }

}
