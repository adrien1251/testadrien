import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InscriptionFournComponent } from './inscription-fourn.component';

describe('InscriptionFournComponent', () => {
  let component: InscriptionFournComponent;
  let fixture: ComponentFixture<InscriptionFournComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InscriptionFournComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InscriptionFournComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
