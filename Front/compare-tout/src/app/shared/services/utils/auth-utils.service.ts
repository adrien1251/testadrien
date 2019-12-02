import {EventEmitter} from '@angular/core';
import {HttpHeaders} from '@angular/common/http';

export class AuthUtils {
  private static AuthNamespaces = {
    CURRENT_USER: 'CURRENT_USER',
  };

  userEmitter: EventEmitter<any> = new EventEmitter();

  constructor() { }

  public static getCurrentRoleUser(): string {
    console.log(this.getCurrentUser());
    return this.getCurrentUser() ? this.getCurrentUser().user.type : null;
  }

  public static getCurrentToken(): string {
    return this.getCurrentUser() ? this.getCurrentUser().jwttoken : null;
  }

  public static getBearerToken(){
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': AuthUtils.getCurrentToken()
    });
  }

  public static getCurrentUser(): any {
    const cuStr = localStorage.getItem(AuthUtils.AuthNamespaces.CURRENT_USER);
    if (cuStr) {
      return JSON.parse(cuStr);
    }
    return null;
  }

  public setCurrentUser(user) {
    localStorage.setItem(AuthUtils.AuthNamespaces.CURRENT_USER, JSON.stringify(user));
    this.userEmitter.emit(user);
  }

  public dispose() {
    localStorage.removeItem(AuthUtils.AuthNamespaces.CURRENT_USER);
    this.userEmitter.emit(null);
  }
}
