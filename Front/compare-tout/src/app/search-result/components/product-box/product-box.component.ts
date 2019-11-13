import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { Product } from 'src/app/shared/models/product.interface';

@Component({
  selector: 'app-product-box',
  templateUrl: './product-box.component.html',
  styleUrls: ['./product-box.component.scss'],
})
export class ProductBoxComponent implements OnInit, OnDestroy {

  @Input() product: Product;

  constructor(
  ) { }

  ngOnInit(): void {
  }

  ngOnDestroy(): void {

  }

}
