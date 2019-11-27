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
  minValue = this.min;
  maxValue = this.max;
  options: Options  = {
  };

  @Output() valueChecked: EventEmitter<any> = new EventEmitter<any>();

  constructor(
  ) {
    this.options  = {
        floor: this.min ? this.min : 0,
        ceil: this.max ? this.max : 50,
        translate: (value: number): string => {
              return value + '€';
        }
      };
  }

  ngOnInit(): void {
    if (this.criteria.unit === 'null' && this.options.floor ) {
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
    const valueSelect = {
      idCriteria: this.criteria.id,
      minValue: this.minValue,
      maxValue: this.maxValue,
      selected: true
    };
    this.valueChecked.emit(valueSelect);
  }
}
