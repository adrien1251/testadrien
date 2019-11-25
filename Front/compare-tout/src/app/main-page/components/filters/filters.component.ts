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

  constructor(
  ) { }

  ngOnInit(): void {
    if (this.criteriaList != null) {
      console.log(this.criteriaList);
      this.fetchCriteria();
    }
  }

  ngOnDestroy(): void {
  }

  fetchCriteria(): void {
    //
  }

  updateCriteria(event) {
    if (event.selected) {
      this.criteriaSelected.push(event);
    } else {
      const i = this.criteriaSelected.findIndex(x => x.id === event.id);
      this.criteriaSelected.splice(i, 1);
    }
    this.valueHasChanged.emit(this.criteriaSelected);
  }

  showFilters() {
    this.displayFilters = !this.displayFilters;
  }

}
