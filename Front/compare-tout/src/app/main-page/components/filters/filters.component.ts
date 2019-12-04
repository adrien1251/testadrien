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
  // animations: [
  //   trigger('slideInOut', [
  //     state('1', style({
  //       overflow: 'visible',
  //       opacity: '1',
  //       height: '*',
  //     })),
  //     state('0', style({
  //       overflow: 'hidden',
  //       opacity: '0',
  //       height: '0px',
  //     })),
  //     transition('0 => 1', animate('400ms ease-in-out', keyframes([
  //       style({ height: 0, opacity: 0, offset: 0 }),
  //       style({ height: '*', opacity: 0, offset: 0.7 }),
  //       style({ height: '*', opacity: 1, offset: 1 }),
  //     ]))),
  //     transition('1 => 0', animate('250ms ease-in-out', keyframes([
  //       style({ height: '*', opacity: 1, offset: 0 }),
  //       style({ height: '*', opacity: 0, offset: 0.1 }),
  //       style({ height: 0, opacity: 0, offset: 1 }),
  //     ]))),
  //   ])],
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
        c.valueMin = Math.floor(+c.values[0].value);
        c.valueMax = Math.ceil(+c.values[c.values.length - 1].value);
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
        this.criteriaSelected.push(
          { idCriteria: event.idCriteria, value: event.value, minValue: event.minValue, maxValue: event.maxValue });
      } else {
        if (this.criteriaSelected[i].value) {
          this.criteriaSelected[i].value.push(event.value[0]);
        }
        this.criteriaSelected[i].minValue = event.minValue;
        this.criteriaSelected[i].maxValue = event.maxValue;
      }
    } else {
      const i = this.criteriaSelected.findIndex(x => x.idCriteria === event.idCriteria);
      this.criteriaSelected.splice(i, 1);
    }
    this.valueHasChanged.emit(this.criteriaSelected);
    const idx = this.criteriaList.findIndex(c => c.idCriteria === event.idCriteria);
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
