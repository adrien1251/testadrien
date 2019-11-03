import { Component, OnInit, OnDestroy } from '@angular/core';
import { Category } from 'src/app/shared/models/category.interface';
import { CategoryService } from 'src/app/shared/services/category.service';
import { categoryMock1, categoryMock2 } from 'src/app/shared/mocks/category-mock';
import { criteriaMock1 } from 'src/app/shared/mocks/critere-mock';

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.scss'],
})
export class MainPageComponent implements OnInit, OnDestroy {
  public categories: Category[];
  private currentCategory: Category;

  constructor(
    private categoryService: CategoryService,
  ) { }

  ngOnInit(): void {
    this.fetchCategories();
  }

  ngOnDestroy(): void {

  }

  fetchCategories(): void {
    this.categories = [categoryMock1];
    this.categories.push(categoryMock2);
    // this.categoryService.getMainCategories().subscribe(res => this.categories = res);
  }

  fetchCurrentCategory(event): void {
    if (event != null) {
      this.currentCategory = event;
      this.currentCategory.criteriaList = criteriaMock1;
    } else {
      this.currentCategory = null;
    }

  }

}
