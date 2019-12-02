import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {apiEndpoints} from './utils/api-endpoints';
import {environment} from '../../../environments/environment';


@Injectable({
  providedIn: 'root',
})
export class ProductService {

  constructor(
    private httpClient: HttpClient,
  ) {
  }


  env = environment.back_url;

  public getProductsByCategoryAndCriteria(idCat: string, criterias?: any): Observable<any> {
    const body = criterias;
    return this.httpClient.post<any>(`${this.env}${apiEndpoints.getProductsByCategoryAndCriteria(idCat)}`, body);
  }

  public getCriteriasOfProduct(idProduct: string): Observable<any> {
    return this.httpClient.get<any>(`${this.env}${apiEndpoints.getCriteriasOfProduct(idProduct)}`);
  }

}
