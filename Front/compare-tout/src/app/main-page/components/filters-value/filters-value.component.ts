import { Component, OnInit, OnDestroy, Input, Output, EventEmitter } from '@angular/core';
import { Criteria, UniqueCriteria } from 'src/app/shared/models/criteria.interface';

@Component({
  selector: 'app-filters-value',
  templateUrl: './filters-value.component.html',
  styleUrls: ['./filters-value.component.scss'],
})
export class FiltersValueComponent implements OnInit, OnDestroy {

  @Input() criteria: UniqueCriteria;
  @Input() value: any;
  @Output() valueChecked: EventEmitter<any> = new EventEmitter<any>();
  public checked = false;

  constructor(
  ) { }

  ngOnInit(): void {
    if (this.criteria.unit === 'null') {
      this.criteria.unit = '';
      console.log(this.criteria);
      console.log(this.value);
    }
  }

  ngOnDestroy(): void {

  }

  updateFilter(value: any) {
    this.value.selected = !this.value.selected;
    this.value = value;
    const valueSelect = {
      idCriteria: this.criteria.id,
      value: [this.value.value],
      selected: this.value.selected
    };
    this.valueChecked.emit(valueSelect);
  }
}
