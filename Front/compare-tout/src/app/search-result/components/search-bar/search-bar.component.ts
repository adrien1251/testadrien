import { Component, OnInit, OnDestroy, EventEmitter, ElementRef, Input, Output, ViewChild } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Product } from 'src/app/shared/models/product.interface';

@Component({
  selector: 'app-search-bar',
  templateUrl: './search-bar.component.html',
  styleUrls: ['./search-bar.component.scss'],
})
export class SearchBarComponent implements OnInit, OnDestroy {


  constructor(
  ) { }

  maximumCharacters = 250;
  form: FormGroup;
  inputIsFocused = false;
  @Output() searchValue = new EventEmitter();
  @Output() eraseEvent = new EventEmitter();
  @Input() productList: any[];
  @Input() selectedOption: Product;
  @ViewChild('searchInput', { static: false }) searchInput: ElementRef;
  @ViewChild('filterInput', { static: false }) filterInput: ElementRef;
  productType: any;


  onFocus(): void {
    this.inputIsFocused = true;
  }

  emptySearch(): void {
    this.inputIsFocused = false;
    this.searchInput.nativeElement.value = '';
    this.eraseEvent.emit(true);
  }

  onType(value): void {
    if (value.length >= 1) {
      this.searchValue.emit(value);
    }
    if (value.length === 0) {
      this.emptySearch();
      this.productList = null;
    }
  }

  search(value): void {
    if (value.length >= 1) {
      this.searchValue.emit(value);
      this.searchInput.nativeElement.value = '';
      this.productList = null;
    } else {
      this.emptySearch();
    }
  }

  ngOnInit(): void {
  }

  ngOnDestroy(): void {

  }

}
