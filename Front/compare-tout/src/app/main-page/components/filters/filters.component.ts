import { Component, OnInit, OnDestroy, Input, Output, EventEmitter, OnChanges } from '@angular/core';
import { Criteria, UniqueCriteria } from 'src/app/shared/models/criteria.interface';
import { trigger, transition, style, animate, state, keyframes  } from '@angular/animations';

@Component({
  selector: 'app-filters',
  templateUrl: './filters.component.html',
  styleUrls: ['./filters.component.scss'],
  animations: [
    trigger('slideInOut', [
      state('1', style({
        height: '*',
      })),
      state('0', style({
        opacity: '0',
        overflow: 'scroll',
        height: '0px',
      })),
      transition('1 <=> 0', animate('100ms ease-in-out')),
    ]),
  ],
})
export class FiltersComponent implements OnInit, OnChanges, OnDestroy {

  @Input() criteriaList: any[];
  criteriaSelected: any[] = [];
  @Output() valueHasChanged: EventEmitter<any> = new EventEmitter<any>();
  displayFilters = false;
  valueMin: number;
  valueMax: number;
  showFilter = false;
  currentCriteria: any = 1;

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
        c.defMinValue = Math.floor(+c.values[0].value);
        c.defMaxValue = Math.ceil(+c.values[c.values.length - 1].value);
        c.minValue = c.minValue != null ? c.minValue : Math.floor(+c.values[0].value);
        c.maxValue = c.maxValue != null ? c.maxValue : Math.ceil(+c.values[c.values.length - 1].value);
      }
      if (c.unit === 'null') {
        c.unit = '';
      }

    });
  }

  updateCriteria(event) {
    if (event.selected) {
      const i = this.criteriaSelected.findIndex(t => t.idCriteria === event.idCriteria);
      if (i === -1) {
        let v;
        if (event.values) {
          v = [];
          event.values.forEach(val => {
            if (val.selected) {
              v.push(val.value);
            }
          });
        }
        this.criteriaSelected.push(
          { idCriteria: event.idCriteria, value: v, minValue: event.minValue, maxValue: event.maxValue });
      } else {
        let v;
        if (event.values) {
          v = [];
          event.values.forEach(val => {
            if (val.selected) {
              v.push(val.value);
            }
          });
        }

        if (this.criteriaSelected[i].value) {
          this.criteriaSelected[i].value = v;
        }
        this.criteriaSelected[i].minValue = event.minValue;
        this.criteriaSelected[i].maxValue = event.maxValue;
      }
    } else {
      const i = this.criteriaSelected.findIndex(x => x.idCriteria === event.idCriteria);
      this.criteriaSelected[i].value = [];
      event.values.forEach(v => {
        if (v.selected) {
          this.criteriaSelected[i].value.push(v.value);
        }
      });
      if (this.criteriaSelected[i].value.length < 1) {
        this.criteriaSelected.splice(i, 1);
      }
    }
    this.valueHasChanged.emit(this.criteriaSelected);
    const idx = this.criteriaList.findIndex(c => {
      if (c.id) {
        return c.id === event.idCriteria;

      } else {
        return c.idCriteria === event.idCriteria;
      }
    });
    this.criteriaList[idx] = event;
  }

  showFilters() {
    this.displayFilters = !this.displayFilters;
  }

  resetFilters(): void {
    this.criteriaSelected = [];
    this.valueHasChanged.emit(this.criteriaSelected);
  }

  animFilters(crit) {
    this.showFilter = !this.showFilter;
    return this.showFilter;
  }

}
