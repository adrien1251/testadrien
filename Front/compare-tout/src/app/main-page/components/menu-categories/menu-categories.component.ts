import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { Category } from 'src/app/shared/models/category.interface';

@Component({
  selector: 'app-menu-categories',
  templateUrl: './menu-categories.component.html',
  styleUrls: ['./menu-categories.component.scss'],
})
export class MenuCategoriesComponent implements OnInit, OnDestroy {
  @Input() categories: Category[];
  isTopCategory = true;
  subCategories: Category[];

  constructor(
  ) { }

  ngOnInit(): void {
  }

  ngOnDestroy(): void {
  }

  goToSubcategory(category: Category): void {
    if (category.childList) {
      this.categories = [category];
      this.subCategories = category.childList;
      this.isTopCategory = false;
    }
  }

}
