import { Component, OnInit, OnDestroy, Input, Output, EventEmitter } from '@angular/core';
import { Criteria, UniqueCriteria } from 'src/app/shared/models/criteria.interface';

@Component({
  selector: 'app-filters',
  templateUrl: './filters.component.html',
  styleUrls: ['./filters.component.scss'],
})
export class FiltersComponent implements OnInit, OnDestroy {

  @Input() criteriaList: Criteria[];
  public uniqueCriteria: UniqueCriteria[] = [];
  @Output() valueHasChanged: EventEmitter<any> = new EventEmitter<any>();
  displayFilters = false;

  constructor(
  ) { }

  ngOnInit(): void {
    if (this.criteriaList != null) {
      this.fetchCriteria();
    }
  }

  ngOnDestroy(): void {
  }

  fetchCriteria(): void {
    console.log(this.criteriaList);
    console.log(this.uniqueCriteria);
    // this.criteriaList.forEach(crit => {
    //   const alreadyIn = this.uniqueCriteria.find(c => c.name === crit.name) != null;
    //   if (!alreadyIn) {
    //     const criteria: UniqueCriteria = {
    //       name: crit.name,
    //       isMandatory: crit.isMandatory,
    //       type: crit.type,
    //       unit: crit.unit,
    //       values: [crit.value]
    //     };
    //     this.uniqueCriteria.push(criteria);
    //   } else {
    //     const idx = this.uniqueCriteria.findIndex(c => c.name === crit.name);
    //     if (idx !== -1) {
    //       if (this.uniqueCriteria[idx].values.find(v => v === crit.value) == null) {
    //         this.uniqueCriteria[idx].values.push(crit.value);
    //       }
    //     }
    //   }
    // });
    console.log(this.criteriaList);
    console.log(this.uniqueCriteria);
  }

  updateCriteria(event) {
    this.valueHasChanged.emit(event);
  }

  showFilters() {
    this.displayFilters = !this.displayFilters;
  }

}
