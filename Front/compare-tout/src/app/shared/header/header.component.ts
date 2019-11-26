import {Component, EventEmitter, OnDestroy, OnInit, Output} from '@angular/core';
import {ModalService} from '../services/modal/modal.service';
import {LoginComponent} from '../../Auth/pages/login/login.component';
import {AuthUtils} from '../services/utils/auth-utils.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit, OnDestroy {
  user = null;

  @Output() toggleSideNav: EventEmitter<any> = new EventEmitter<any>();
  constructor(
    private modalService: ModalService,
    private authUtils: AuthUtils) { }

  ngOnInit(): void {
    this.user = AuthUtils.getCurrentUser();
    this.authUtils.userEmitter.subscribe((user) => {
      this.user = user;
    });
  }

  ngOnDestroy(): void {
  }

  openConnexion() {
    this.modalService.init(LoginComponent, {}, {});
  }

  openSideNav() {
    this.toggleSideNav.emit();
  }
}
