import { Component, OnInit, OnDestroy } from '@angular/core';
import { Category } from 'src/app/shared/models/category.interface';
import { CategoryService } from 'src/app/shared/services/category.service';
import { CriteriaService } from 'src/app/shared/services/criteria.service';
import { Product } from 'src/app/shared/models/product.interface';
import { ProductService } from 'src/app/shared/services/product.service';
import { Criteria, UniqueCriteria } from 'src/app/shared/models/criteria.interface';
import { ActivatedRoute, Router } from '@angular/router';

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
  fromProduct = false;


  constructor(
    private categoryService: CategoryService,
    private criteriaService: CriteriaService,
    private productService: ProductService,
    private route: ActivatedRoute,
    private router: Router,
  ) {
    this.currentCategory = this.router.getCurrentNavigation().extras.state ? this.router.getCurrentNavigation().extras.state.cat : null;
    this.fromProduct = this.currentCategory != null;
  }

  ngOnInit(): void {
    if (!this.fromProduct && !this.currentCategory) {
      this.fetchCategories();
    } else {
      const idRoute = this.route.snapshot.paramMap.get('id');
      if (idRoute && idRoute !== 'all') {
        this.fetchCurrentCategory(this.currentCategory, this.fromProduct);
      } else {
        this.fetchCategories();
      }
    }
  }

  ngOnDestroy(): void {

  }

  fetchCategories(): void {
    this.subCategories = null;
    this.currentCategory = null;
    this.categories = null;
    this.categoryService.getCategories().subscribe(res => {
      if (res != null && res.length !== 0) {
        this.categories = res;
      }
    }
    );
  }

  fetchCurrentCategory(event, fromRoute?: boolean): void {
    if (event != null) {
      if (fromRoute) {
        this.categoryService.getCategoriesChild(event.id).subscribe((res) => {
          this.isChildCategory = res.length === 0;
          this.subCategories = res;
          this.categories = [event];

        });
      } else {
        this.categoryService.getCategoriesChild(event.id).subscribe((res) => {
          this.isChildCategory = res.length === 0;
          this.subCategories = res;
        });
        this.currentCategory = event;
      }
      this.categoryService.setCurrentCategory(this.currentCategory);
      if (this.currentCategory) {
        this.criteriaService.getCriterias(this.currentCategory.id).subscribe((res) => {
          this.currentCategory.criteriaList = res;
          this.fetchProducts();
        });
      }
    } else {
      this.productList = null;
      this.currentCategory = null;
      this.subCategories = null;
      this.fetchCategories();
    }

  }

  fetchProducts(): void {
    this.productService.getProductsByCategoryAndCriteria(this.currentCategory.id, null).subscribe((res) => {
      this.productList = res;
      this.criteriaService.getCriteriasValues(this.currentCategory.id).subscribe(criterias => {
        this.productList.forEach(product => {
          this.productService.getCriteriasOfProduct(product.id).subscribe(crit => {
            crit.forEach(criteria => {
              const alreadyIn = this.criteriaValues.find(c => c.id === criteria.id) != null;
              if (!alreadyIn) {
                const newCrit: UniqueCriteria = {
                  id: criteria.id,
                  name: criteria.criteriaName,
                  type: criteria.type,
                  unit: criteria.criteriaUnit,
                  values: criterias[criteria.id],
                };
                this.criteriaValues.push(newCrit);
              }
            });
            this.canShowFilters = true;
          });
        });
      });
    });
  }

  sendCriterias(event): void {
    if (event == null || event.length === 0) { event = null; }
    this.productService.getProductsByCategoryAndCriteria(this.currentCategory.id, event).subscribe(res => {
      this.productList = res;
    }
    );
  }

}
