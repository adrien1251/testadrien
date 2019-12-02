import { Component, OnInit, OnDestroy, Input, Output, EventEmitter, OnChanges } from '@angular/core';
import { Criteria, UniqueCriteria } from 'src/app/shared/models/criteria.interface';

@Component({
  selector: 'app-filters',
  templateUrl: './filters.component.html',
  styleUrls: ['./filters.component.scss'],
})
export class FiltersComponent implements OnInit, OnChanges, OnDestroy {

  @Input() criteriaList: any[];
  criteriaSelected: any[] = [];
  @Output() valueHasChanged: EventEmitter<any> = new EventEmitter<any>();
  displayFilters = false;
  valueMin: number;
  valueMax: number;

  constructor(
  ) { }

  ngOnInit(): void {
    if (this.criteriaList != null && this.criteriaList.length > 0) {
      this.fetchCriteria();
    }
  }

  ngOnChanges(): void {
    if (this.criteriaList != null && this.criteriaList.length > 0) {
      this.fetchCriteria();
    }
  }

  ngOnDestroy(): void {
  }

  fetchCriteria(): void {
    this.criteriaList.forEach(c => {
      if (c.type === 'INT' || c.type === 'FLOAT') {
        this.valueMin = Math.floor(+c.values[0]);
        this.valueMax = +c.values[c.values.length - 1];
      }
    });
  }

  updateCriteria(event) {
    if (event.selected) {
      const i = this.criteriaSelected.findIndex(t => t.id === event.id);
      if (i === -1) {
        this.criteriaSelected.push(
          { idCriteria: event.idCriteria, value: event.value, minValue: event.minValue, maxValue: event.maxValue });
      } else {
        this.criteriaSelected[i] = { idCriteria: event.idCriteria, value: event.value, minValue: event.minValue, maxValue: event.maxValue };
      }
    } else {
      const i = this.criteriaSelected.findIndex(x => x.id === event.id);
      this.criteriaSelected.splice(i, 1);
    }
    this.valueHasChanged.emit(this.criteriaSelected);
  }

  showFilters() {
    this.displayFilters = !this.displayFilters;
  }

  resetFilters(): void {
    this.criteriaSelected = [];
    this.valueHasChanged.emit(this.criteriaSelected);
  }

}
