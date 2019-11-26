import { Component } from '@angular/core';
import {ModalService} from './shared/services/modal/modal.service';
import {DomService} from './shared/services/modal/dom.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  providers: [ModalService, DomService]
})
export class AppComponent {

  title = 'compare-tout';

  constructor(private modalService: ModalService) {}

  removeModal(){
    this.modalService.destroy();
  }
}
