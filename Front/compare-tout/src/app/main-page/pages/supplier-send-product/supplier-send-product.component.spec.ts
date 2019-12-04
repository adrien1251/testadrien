import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SupplierSendProductComponent } from './supplier-send-product.component';

describe('SupplierSendProductComponent', () => {
  let component: SupplierSendProductComponent;
  let fixture: ComponentFixture<SupplierSendProductComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SupplierSendProductComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SupplierSendProductComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
