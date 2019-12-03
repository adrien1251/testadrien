import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {apiEndpoints} from './utils/api-endpoints';
import {environment} from '../../../environments/environment';


@Injectable({
  providedIn: 'root',
})
export class TestService {

  user: any;
  users: any;

  constructor(
    private httpClient: HttpClient,
  ) {
  }


  public displayBack(): Observable<any> {
    return this.httpClient.get<any>(`${environment.back_url}${apiEndpoints.getUsers}`);
  }
}
