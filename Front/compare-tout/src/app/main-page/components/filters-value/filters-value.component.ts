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
    }
  }

  ngOnDestroy(): void {

  }

  updateFilter(value: any) {
    this.value.selected = !this.value.selected;
    this.value = value;
    this.criteria.values.find(v => v.value === value.value);
    const valueSelect = {
      idCriteria: this.criteria.id ? this.criteria.id : this.criteria.idCriteria,
      values: this.criteria.values,
      name: this.criteria.name,
      selected: this.value.selected,
      type: this.criteria.type,
      unit: this.criteria.unit,
    };
    this.valueChecked.emit(valueSelect);
  }
}
