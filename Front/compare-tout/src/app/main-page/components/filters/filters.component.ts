import { Component, OnInit, OnDestroy, Input, Output, EventEmitter } from '@angular/core';
import { Criteria, UniqueCriteria } from 'src/app/shared/models/criteria.interface';

@Component({
  selector: 'app-filters',
  templateUrl: './filters.component.html',
  styleUrls: ['./filters.component.scss'],
})
export class FiltersComponent implements OnInit, OnDestroy {

  @Input() criteriaList: any[];
  criteriaSelected: any[] = [];
  @Output() valueHasChanged: EventEmitter<any> = new EventEmitter<any>();
  displayFilters = false;
  valueMin = 99999;
  valueMax = 0;

  constructor(
  ) { }

  ngOnInit(): void {
    if (this.criteriaList != null && this.criteriaList.length > 0) {
      this.fetchCriteria();
    }
  }

  ngOnDestroy(): void {
  }

  fetchCriteria(): void {
    // this.criteriaList.forEach(c => {
    //   if (c.type === 'INT' || c.type === 'FLOAT') {
    //     c.values.forEach(crit => {
    //       if (+crit < this.valueMin) {
    //         this.valueMin = +crit;
    //         c.minValue = +crit;
    //       } else {
    //         if (+crit > this.valueMax) {
    //           this.valueMax = +crit;
    //           c.maxValue = +crit;
    //         }
    //       }
    //     });
    //   }
    // });
  }

  updateCriteria(event) {
    if (event.selected) {
      this.criteriaSelected.push( { idCriteria: event.idCriteria, value: event.value, minValue: event.minValue, maxValue: event.maxValue });
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
