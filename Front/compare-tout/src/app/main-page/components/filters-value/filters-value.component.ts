import { Component, OnInit, OnDestroy, Input, Output, EventEmitter } from '@angular/core';
import { Criteria, UniqueCriteria } from 'src/app/shared/models/criteria.interface';

@Component({
  selector: 'app-filters-value',
  templateUrl: './filters-value.component.html',
  styleUrls: ['./filters-value.component.scss'],
})
export class FiltersValueComponent implements OnInit, OnDestroy {

  @Input() criteria: UniqueCriteria;
  @Input() value: string;
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

  updateFilter(value: string) {
    this.checked = !this.checked;
    console.log(this.checked);
    const valueSelect = {
      idCriteria: this.criteria.id,
      value: [value],
      selected: this.checked
    };
    this.valueChecked.emit(valueSelect);
  }
}
