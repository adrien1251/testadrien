import { Injectable } from '@angular/core';
import {Router, CanActivate, ActivatedRouteSnapshot} from '@angular/router';
import {AuthUtils} from '../utils/auth-utils.service';

@Injectable()
export class AuthGuardAdminService implements CanActivate {
  constructor(public router: Router) {}

  canActivate(route: ActivatedRouteSnapshot): boolean {
    const expectedRole = route.data.expectedRole;
    if (AuthUtils.getCurrentRoleUser() !== expectedRole) {
      this.router.navigate(['/']);
      return false;
    }
    return true;
  }
}
