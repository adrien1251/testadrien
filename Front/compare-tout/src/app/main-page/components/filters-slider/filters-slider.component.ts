import { Component, OnInit, OnDestroy, Input, Output, EventEmitter } from '@angular/core';
import { Criteria, UniqueCriteria } from 'src/app/shared/models/criteria.interface';
import { Options, LabelType } from 'ng5-slider';

@Component({
  selector: 'app-filters-slider',
  templateUrl: './filters-slider.component.html',
  styleUrls: ['./filters-slider.component.scss'],
})
export class FiltersSliderComponent implements OnInit, OnDestroy {

  @Input() criteria: UniqueCriteria;
  @Input() min;
  @Input() max;
  @Input() value: string;
  options: Options = {
  };

  @Output() valueChecked: EventEmitter<any> = new EventEmitter<any>();

  constructor(
  ) {
  }

  ngOnInit(): void {
    this.options = {
      floor: Math.floor(this.min),
      ceil: Math.ceil(this.max),
      translate: (value: number): string => {
        return value + '€';
      }
    };
    if (this.criteria.unit === 'null' && this.options.floor) {
      this.criteria.unit = '';
    }
  }

  ngOnDestroy(): void {

  }

  updateLow(event) {
    this.updateFilter(event, null);
  }

  updateHigh(event) {
    this.updateFilter(null, event);
  }

  updateFilter(min, max) {
    if (min) {
      const valueSelect = {
        idCriteria: this.criteria.id,
        minValue: min,
        selected: true
      };
      this.valueChecked.emit(valueSelect);
    }
    if (max) {
      const valueSelect = {
        idCriteria: this.criteria.id,
        maxValue: max,
        selected: true
      };
      this.valueChecked.emit(valueSelect);
    }
  }
}
