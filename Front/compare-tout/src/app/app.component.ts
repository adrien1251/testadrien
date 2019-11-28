import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {ModalService} from './shared/services/modal/modal.service';
import {DomService} from './shared/services/modal/dom.service';
import {AuthUtils} from './shared/services/utils/auth-utils.service';
import {AuthComponent} from './Auth/pages/auth/auth.component';
import {Router} from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  providers: [ModalService, DomService]
})
export class AppComponent implements OnInit {

  title = 'compare-tout';
  user = null;

  constructor(
    private modalService: ModalService,
    private router: Router,
    private authUtils: AuthUtils) {}

  ngOnInit(): void {
    this.user = AuthUtils.getCurrentUser();
    this.authUtils.userEmitter.subscribe((user) => {
      this.user = user;
    });
  }

  removeModal() {
    this.modalService.destroy();
  }

  openConnexion(){
    this.modalService.init(AuthComponent, {}, {});
  }

  logout() {
    this.authUtils.dispose();
    this.router.navigate(['/']);
  }
}
