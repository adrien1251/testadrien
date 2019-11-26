export class AuthUtils {

  private static AuthNamespaces = {
    CURRENT_USER: 'CURRENT_USER',
  };

  public static dispose() {
    localStorage.removeItem(AuthUtils.AuthNamespaces.CURRENT_USER);
  }

  public static setCurrentUser(user) {
    localStorage.setItem(AuthUtils.AuthNamespaces.CURRENT_USER, JSON.stringify(user));
  }

  public static getCurrentUser(): any {
    const cuStr = localStorage.getItem(AuthUtils.AuthNamespaces.CURRENT_USER);
    if (cuStr) {
      return JSON.parse(cuStr);
    }
    return null;
  }
}
