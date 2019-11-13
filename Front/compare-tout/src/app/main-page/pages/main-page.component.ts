import { Component, OnInit, OnDestroy } from '@angular/core';
import { Category } from 'src/app/shared/models/category.interface';
import { CategoryService } from 'src/app/shared/services/category.service';
import { categoryMock1, categoryMock2 } from 'src/app/shared/mocks/category-mock';
import { criteriaMock1 } from 'src/app/shared/mocks/critere-mock';
import { CriteriaService } from 'src/app/shared/services/criteria.service';

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.scss'],
})
export class MainPageComponent implements OnInit, OnDestroy {
  public categories: Category[];
  private currentCategory: Category;
  private subCategories: Category[];

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
    this.categoryService.getMainCategories().subscribe(res => {
      if (res != null && res.length !== 0) {
        this.categories = res;
      } else {
        this.categories = [categoryMock1];
        this.categories.push(categoryMock2);
      }
    }
    );
  }

  fetchCurrentCategory(event): void {
    if (event != null) {
      this.categoryService.getChildCategories(event.id).subscribe((res) => {
        this.subCategories = res;
      });
      this.subCategories = event.childList;
      this.currentCategory = event;
      this.currentCategory.criteriaList = criteriaMock1;
    } else {
      this.currentCategory = null;
    }
    this.criteriaService.getCriterias(this.currentCategory.id).subscribe((res) => {
      this.currentCategory.criteriaList = res;
    });

  }

}
