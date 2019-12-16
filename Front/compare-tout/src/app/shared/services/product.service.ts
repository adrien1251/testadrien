import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {apiEndpoints} from './utils/api-endpoints';
import {environment} from '../../../environments/environment';


@Injectable({
  providedIn: 'root',
})
export class ProductService {
  currentProduct: any;
  constructor(
    private httpClient: HttpClient,
  ) {
  }

  public getProductsByCategoryAndCriteria(idCat: string, criterias?: any): Observable<any> {
    const body = criterias;
    return this.httpClient.post<any>(`${environment.back_url}${apiEndpoints.getProductsByCategoryAndCriteria(idCat)}`, body);
  }

  public getCriteriasOfProduct(idProduct: string): Observable<any> {
    return this.httpClient.get<any>(`${environment.back_url}${apiEndpoints.getCriteriasOfProduct(idProduct)}`);
  }

  public getCurrentProduct(): any {
    return this.currentProduct;
}

  public setCurrentProduct(cat: any): void {
      this.currentProduct = cat;
  }
}
