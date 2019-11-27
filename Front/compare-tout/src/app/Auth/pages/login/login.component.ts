import {Component, Input, OnInit, Output} from '@angular/core';
import { ModalService} from '../../../shared/services/modal/modal.service';
import {FormControl, FormGroup} from '@angular/forms';
import {EventEmitter} from 'events';
import {AuthService} from '../../../shared/services/auth.service';
import {Error} from '../../../shared/models/error.interface';
import {AuthUtils} from '../../../shared/services/utils/auth-utils.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  @Input() error: string | null;

  isWaiting = false;

  form: FormGroup = new FormGroup({
    username: new FormControl(''),
    password: new FormControl(''),
  });

  constructor(
    private modalService: ModalService,
    private authService: AuthService,
    private authUtils: AuthUtils) { }

  ngOnInit() {
  }


  close() {
    this.modalService.destroy();
  }

  submit() {
    if (this.form.valid) {
      this.isWaiting = true;
      this.authService.login(this.form.value.username, this.form.value.password).subscribe(
        (user: any) => {
          this.error = null;
          this.authUtils.setCurrentUser(user);
        },
        (error: Error) => {
          if (error.error.statusErrorCode === 404) {
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
    }
  }
}
