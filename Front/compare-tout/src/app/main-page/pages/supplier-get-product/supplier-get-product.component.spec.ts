import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SupplierGetProductComponent } from './supplier-get-product.component';

describe('SupplierGetProductComponent', () => {
  let component: SupplierGetProductComponent;
  let fixture: ComponentFixture<SupplierGetProductComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SupplierGetProductComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SupplierGetProductComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
