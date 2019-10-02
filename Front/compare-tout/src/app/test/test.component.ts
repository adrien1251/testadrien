// .subscribe(res => {
//     this.user = res[0];
//     this.users = res;
// });

import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { TestService } from '../shared/services/test.service';

@Component({
  // tslint:disable-next-line:component-selector
  selector: 'test',
  templateUrl: './test.component.html',
  styleUrls: ['./test.component.scss'],
})
export class TestComponent implements OnInit {
  users: any;

  constructor(
      private testService: TestService,
  ) { }

  ngOnInit(): void {
    this.display();
  }

  public display(): void {
    this.testService.displayBack().subscribe(res => this.users = res);
  }
}
