import { Component, OnInit, OnDestroy, OnChanges } from '@angular/core';
import { Category } from 'src/app/shared/models/category.interface';
import { CategoryService } from 'src/app/shared/services/category.service';
import { CriteriaService } from 'src/app/shared/services/criteria.service';
import { Product } from 'src/app/shared/models/product.interface';
import { ProductService } from 'src/app/shared/services/product.service';
import { Criteria, UniqueCriteria } from 'src/app/shared/models/criteria.interface';
import { ActivatedRoute, Router } from '@angular/router';
import { MatDialog, MatDialogRef } from '@angular/material';
import { ComparisonPopupComponent } from '../components/comparison-popup/comparison-popup.component';
import { timer } from 'rxjs';
import { debounce } from 'rxjs/operators';

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.scss'],
})
export class MainPageComponent implements OnInit, OnChanges, OnDestroy {
  public categories: Category[];
  public currentCategory: Category;
  public subCategories: Category[];
  public isChildCategory: boolean;
  public productList: Product[];
  public criteriaList: Criteria[] = [];
  public criteriaValues: UniqueCriteria[] = [];
  public dialogRefCompareProd: MatDialogRef<ComparisonPopupComponent>;
  comparisonProduct: Product[] = [];
  canShowFilters = false;
  fromProduct = false;
  numberOfComparison = 1;
  filArianne: string[] = [];


  constructor(
    private categoryService: CategoryService,
    private criteriaService: CriteriaService,
    private productService: ProductService,
    private route: ActivatedRoute,
    private router: Router,
    public dialog: MatDialog,
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
        this.filArianne = [];
        this.fetchCurrentCategory(this.currentCategory, this.fromProduct);
      } else {
        this.fetchCategories();
        this.numberOfComparison = 0;
        this.comparisonProduct = [];
      }
    }
  }

  ngOnChanges() {
    const idRoute = this.route.snapshot.paramMap.get('id');
    if (idRoute && idRoute !== 'all') {
      this.filArianne = [];
      this.fetchCurrentCategory(this.currentCategory, this.fromProduct);
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
    this.filArianne.push(event);
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
              const alreadyIn = this.criteriaValues.find(c => c.idCriteria === criteria.id) != null;
              if (!alreadyIn) {
                const criteriaSelected = [];
                criterias[criteria.id].forEach(value => {
                  const v = {
                    value,
                    selected: false,
                  };
                  criteriaSelected.push(v);
                });
                criterias[criteria.id] = criteriaSelected;
                const newCrit: UniqueCriteria = {
                  idCriteria: criteria.id,
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
    this.productService.getProductsByCategoryAndCriteria(this.currentCategory.id, event)
      .pipe(debounce(val => timer(1500)))
      .subscribe(res => {
        this.productList = res;
      }
      );
  }

  compareProduct(event) {
    this.numberOfComparison += 1;
    this.comparisonProduct.push(event);
    if (this.comparisonProduct.length === 2) {
      this.dialogRefCompareProd = this.dialog.open(ComparisonPopupComponent, {
        data: {
          products: this.comparisonProduct,
          category: this.currentCategory,
        }
      });
      this.dialogRefCompareProd.afterClosed().subscribe(res => {
        this.numberOfComparison = 0;
        this.comparisonProduct = [];
        this.fetchProducts();

      });
    }
  }

  fromBack() {
    this.filArianne = [];
  }

  goToCategory(f) {
    this.router.navigate(['/category', f.id]);
  }
}
