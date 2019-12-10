import {EventEmitter} from '@angular/core';

export class AuthUtils {
  private static AuthNamespaces = {
    CURRENT_USER: 'CURRENT_USER',
  };

  userEmitter: EventEmitter<any> = new EventEmitter();

  constructor() { }

  private static getCurrentStorageUser(): any {
    const cuStr = localStorage.getItem(AuthUtils.AuthNamespaces.CURRENT_USER);
    if (cuStr) {
      return JSON.parse(cuStr);
    }
    return null;
  }

  public static getCurrentRoleUser(): string {
    return this.getCurrentUser() ? this.getCurrentUser().type : null;
  }

  public static getCurrentToken(): string {
    return this.getCurrentStorageUser() ? this.getCurrentStorageUser().jwttoken : null;
  }

  public static getCurrentUser(): any {
    return this.getCurrentStorageUser() ? this.getCurrentStorageUser().user : null;
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
