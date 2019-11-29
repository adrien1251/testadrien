import { Component, OnInit, OnDestroy, Input, EventEmitter, Output, OnChanges } from '@angular/core';
import { Category } from 'src/app/shared/models/category.interface';
import { Router } from '@angular/router';

@Component({
  selector: 'app-menu-categories',
  templateUrl: './menu-categories.component.html',
  styleUrls: ['./menu-categories.component.scss'],
})
export class MenuCategoriesComponent implements OnInit, OnChanges, OnDestroy {
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
    this.isTopCategory = this.router.url.includes('all');
  }

  ngOnChanges(): void {
    this.isTopCategory = this.router.url.includes('all');
  }

  ngOnDestroy(): void {
  }

  goBack(): void {
    this.isTopCategory = true;
    this.seeMore = false;
    this.categoriesDisplay = this.categories;
    this.subCategories = null;
    this.currentCategory.emit(null);
    this.router.navigate(['/category/all']);
  }

  goToSubcategory(category: Category): void {
    this.categoriesDisplay = [category];
    this.currentCategory.emit(category);
    this.router.navigate(['/category', category.id]);
    this.isTopCategory = false;
  }

  displayMore() {
    this.seeMore = !this.seeMore;
  }

}
