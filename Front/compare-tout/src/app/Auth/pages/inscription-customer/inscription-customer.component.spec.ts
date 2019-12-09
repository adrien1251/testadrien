import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InscriptionCustomerComponent } from './inscription-customer.component';

describe('InscriptionCustomerComponent', () => {
  let component: InscriptionCustomerComponent;
  let fixture: ComponentFixture<InscriptionCustomerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InscriptionCustomerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InscriptionCustomerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
