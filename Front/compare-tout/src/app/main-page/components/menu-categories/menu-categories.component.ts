import { Component, OnInit, OnDestroy, Input, EventEmitter, Output } from '@angular/core';
import { Category } from 'src/app/shared/models/category.interface';
import { criteriaMock1 } from 'src/app/shared/mocks/critere-mock';
import { Router } from '@angular/router';

@Component({
  selector: 'app-menu-categories',
  templateUrl: './menu-categories.component.html',
  styleUrls: ['./menu-categories.component.scss'],
})
export class MenuCategoriesComponent implements OnInit, OnDestroy {
  @Input() categories: Category[];
  categoriesDisplay: Category[];
  isTopCategory = true;
  seeMore = false;
  @Output() currentCategory: EventEmitter<Category> = new EventEmitter<Category>();
  @Input() subCategories: Category[];

  constructor(
    private router: Router,
  ) { }

  ngOnInit(): void {
    this.categoriesDisplay = this.categories;
  }

  ngOnDestroy(): void {
  }

  goBack(): void {
    this.isTopCategory = true;
    this.seeMore = false;
    this.categoriesDisplay = this.categories;
    this.subCategories = null;
    this.currentCategory.emit(null);
  }

  goToSubcategory(category: Category): void {
    this.isTopCategory = false;
    if (category.childList) {
      this.isTopCategory = false;
    }
    this.categoriesDisplay = [category];
    this.currentCategory.emit(category);
    this.router.navigate(['/category', category.id]);

  }

  displayMore() {
    this.seeMore = !this.seeMore;
  }

}
