import { Component, OnInit, OnDestroy, ɵConsole, Input } from '@angular/core';
import { Product } from 'src/app/shared/models/product.interface';
import { Router } from '@angular/router';

@Component({
  selector: 'app-product-compare',
  templateUrl: './product-compare.component.html',
  styleUrls: ['./product-compare.component.scss'],
})
export class ProductCompareComponent implements OnInit, OnDestroy {

  @Input()product: any;
  @Input()product2: any;
  criteriaValues: any[] = [];
  category: any ;


  constructor(
    private router: Router,
  ) {
  }

  ngOnInit(): void {
    let i = 0;
    this.product.criteriaDtoList.forEach(c => {
      if (c.criteriaUnit === 'null') { c.criteriaUnit = ''; }
      this.criteriaValues.push(c);
      this.criteriaValues[i].value = [c.value];
      i += 1;
    });
    this.product2.criteriaDtoList.forEach(c => {
        if (c.criteriaUnit === 'null') { c.criteriaUnit = ''; }
        const foundIndex = this.criteriaValues.findIndex(cr => cr.id === c.id);
        if (foundIndex !== -1) {
          this.criteriaValues[foundIndex].value.push(c.value);
        } else {
          this.criteriaValues.push(c);
        }
      });
  }

  ngOnDestroy(): void {

  }


  goBack() {
    this.router.navigate(['/category', this.category.id], { state: { cat: this.category } } );
  }
}
