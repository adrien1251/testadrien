import { Component, OnInit, OnDestroy, Input, EventEmitter, Output } from '@angular/core';
import { Category } from 'src/app/shared/models/category.interface';
import { criteriaMock1 } from 'src/app/shared/mocks/critere-mock';

@Component({
  selector: 'app-menu-categories',
  templateUrl: './menu-categories.component.html',
  styleUrls: ['./menu-categories.component.scss'],
})
export class MenuCategoriesComponent implements OnInit, OnDestroy {
  @Input() categories: Category[];
  categoriesDisplay: Category[];
  isTopCategory = true;
  @Output() currentCategory: EventEmitter<Category> = new EventEmitter<Category>();
  @Input() subCategories: Category[];

  constructor(
  ) { }

  ngOnInit(): void {
    this.categoriesDisplay = this.categories;
  }

  ngOnDestroy(): void {
  }

  goBack(): void {
    this.isTopCategory = true;
    this.categoriesDisplay = this.categories;
    this.subCategories = null;
    this.currentCategory.emit(null);

  }

  goToSubcategory(category: Category): void {
    if (category.childList) {
      this.categoriesDisplay = [category];
      this.isTopCategory = false;
      this.currentCategory.emit(category);
    }
  }

}