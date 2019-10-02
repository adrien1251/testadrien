import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { TestService } from '../shared/services/test.service';

@Component({
  // tslint:disable-next-line:component-selector
  selector: 'test',
  templateUrl: './test.component.html',
  styleUrls: ['./test.component.scss'],
})
export class TestComponent implements OnInit, OnDestroy {
  users: any;
  testSubscription: Subscription;

  constructor(
      private testService: TestService,
  ) { }

  ngOnInit(): void {
    this.display();
  }

  ngOnDestroy(): void {
    if (this.testSubscription) {
      this.testSubscription.unsubscribe();
    }
  }

  public display(): void {
    this.testSubscription = this.testService.displayBack().subscribe(res => this.users = res);
  }
}
