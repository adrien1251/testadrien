import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { Product } from 'src/app/shared/models/product.interface';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.scss'],
})
export class ProductListComponent implements OnInit, OnDestroy {

  @Input() productList: Product[];

  constructor(
  ) { }

  ngOnInit(): void {
  }

  ngOnDestroy(): void {

  }

}
