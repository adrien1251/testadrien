import {Component, Input, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {ModalService} from '../../../shared/services/modal/modal.service';
import {AuthService} from '../../../shared/services/auth.service';
import {AuthUtils} from '../../../shared/services/utils/auth-utils.service';
import {Error} from '../../../shared/models/error.interface';
import {RegisterService} from '../../../shared/services/register.service';
import {Supplier} from '../../../shared/models/supplier.interface';

@Component({
  selector: 'app-inscription-fourn',
  templateUrl: './inscription-fourn.component.html',
  styleUrls: ['./inscription-fourn.component.scss']
})
export class InscriptionFournComponent implements OnInit {

  error: string | null;

  isWaiting = false;

  form: FormGroup = new FormGroup({
    firstName: new FormControl('',  [Validators.required]),
    lastName: new FormControl('',  [Validators.required]),
    email: new FormControl('',  [Validators.required, Validators.email]),
    password: new FormControl('',  [Validators.required]),
    webSite: new FormControl('',  [Validators.required]),
    siret: new FormControl('',  [Validators.required]),
  });

  constructor(
    private modalService: ModalService,
    private registerService: RegisterService) { }

  ngOnInit() {
  }


  close() {
    this.modalService.destroy();
  }

  submit() {
    if (this.form.valid) {
      this.isWaiting = true;
      this.registerService.registerSupplier(this.form.value).subscribe(
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
