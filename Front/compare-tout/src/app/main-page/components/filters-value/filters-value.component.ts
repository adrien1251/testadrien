import { Component, OnInit, OnDestroy, Input, Output, EventEmitter } from '@angular/core';
import { Criteria } from 'src/app/shared/models/criteria.interface';

@Component({
  selector: 'app-filters-value',
  templateUrl: './filters-value.component.html',
  styleUrls: ['./filters-value.component.scss'],
})
export class FiltersValueComponent implements OnInit, OnDestroy {

  @Input() value: string;
  @Input() unit: string;
  @Input() id: string;
  @Output() valueChecked: EventEmitter<any> = new EventEmitter<any>();
  public checked = false;

  constructor(
  ) { }

  ngOnInit(): void {
    if (this.unit === 'null') {
      this.unit = '';
    }
  }

  ngOnDestroy(): void {

  }

  updateFilter(value: string) {
    this.checked = !this.checked;
    console.log(this.checked);
    const valueSelect = {
      idCriteria: this.id,
      value,
      selected: this.checked
    };
    this.valueChecked.emit(valueSelect);
  }
}
