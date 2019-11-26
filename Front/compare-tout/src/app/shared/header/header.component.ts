import {Component, OnDestroy, OnInit} from '@angular/core';
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

  constructor(private modalService: ModalService) { }

  ngOnInit(): void {
    this.user = AuthUtils.getCurrentUser();
  }

  ngOnDestroy(): void {
  }

  openConnexion() {
    this.modalService.init(LoginComponent, {isMobile: true}, {});
  }

  openProfil() {
    // TODO : profil page for now --> disconnected
    AuthUtils.dispose();
  }
}
