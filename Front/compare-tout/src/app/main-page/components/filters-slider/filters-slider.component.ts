import { Component, OnInit, OnDestroy, Input, Output, EventEmitter } from '@angular/core';
import { UniqueCriteria } from 'src/app/shared/models/criteria.interface';
import { Options } from 'ng5-slider';

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
  tempMin = this.min;
  tempMax = this.max;
  options: Options = {
  };

  @Output() valueChecked: EventEmitter<any> = new EventEmitter<any>();

  constructor(
  ) {
  }

  ngOnInit(): void {
    this.options = {
      floor: this.criteria.defMinValue,
      ceil: this.criteria.defMaxValue,
    };
    if (this.criteria.unit === 'null' && this.options.floor) {
      this.criteria.unit = '';
    }
  }

  ngOnDestroy(): void {

  }

  updateLow(event) {

    if (event !== this.tempMin) {
      this.updateFilter(event, this.max);
    }
  }

  updateHigh(event) {

    if (event !== this.tempMax) {
      this.updateFilter(this.min, event);
    }
  }

  updateFilter(mini: any, max: any) {
    this.tempMin = mini;
    this.tempMax = max;
    const valueSelect = {
      idCriteria: this.criteria.id ? this.criteria.id : this.criteria.idCriteria,
      minValue: mini,
      maxValue: max,
      name: this.criteria.name,
      defMinValue: this.criteria.defMinValue,
      defMaxValue: this.criteria.defMaxValue,
      type: this.criteria.type,
      unit: this.criteria.unit,
      selected: true
    };
    this.valueChecked.emit(valueSelect);
  }
}
