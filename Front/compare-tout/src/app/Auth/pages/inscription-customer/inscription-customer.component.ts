import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {ModalService} from '../../../shared/services/modal/modal.service';
import {RegisterService} from '../../../shared/services/register.service';
import {Error} from '../../../shared/models/error.interface';

@Component({
  selector: 'app-inscription-customer',
  templateUrl: './inscription-customer.component.html',
  styleUrls: ['./inscription-customer.component.scss']
})
export class InscriptionCustomerComponent implements OnInit {
  error: string | null;

  isWaiting = false;

  sexes = ['Homme', 'Femme', 'Autre'];

  sexeControl = new FormControl('',  [Validators.required]);

  form: FormGroup = new FormGroup({
    firstName: new FormControl('',  [Validators.required]),
    lastName: new FormControl('',  [Validators.required]),
    email: new FormControl('',  [Validators.required, Validators.email]),
    password: new FormControl('',  [Validators.required]),
    phoneNum: new FormControl('',  [Validators.required]),
    sexe: this.sexeControl,
    birthday: new FormControl('',  [Validators.required]),
  });

  constructor(
    private modalService: ModalService,
    private registerService: RegisterService
  ) { }

  ngOnInit() {
  }


  close() {
    this.modalService.destroy();
  }

  submit() {
    if (this.form.valid) {
      this.isWaiting = true;
      this.registerService.registerCustomer(this.form.value).subscribe(
        (user: any) => {
          this.error = null;
        },
        (error: Error) => {
          if (error.error.statusErrorCode === 409) {
            this.error = error.error.errorMessage;
          } else {
            this.error = "Une erreur innatendu s'est produite";
          }
          this.isWaiting = false;
        },
        () => {
          this.isWaiting = false;
          this.close();
        }
      );
    } else {
      this.error = "L'un des champs n'est pas remplit";
    }
  }

}
