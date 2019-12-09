import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {apiEndpoints} from './utils/api-endpoints';
import {environment} from '../../../environments/environment';
import {Supplier} from '../models/supplier.interface';


@Injectable({
  providedIn: 'root',
})
export class AdminService {

  constructor(private httpClient: HttpClient) { }

  public findAllSupplierWhoNeedValidate(): Observable<Supplier[]> {
    return this.httpClient.get<Supplier[]>(`${environment.back_url}${apiEndpoints.findAllSupplierWhoNeedValidate}`);
  }

  public validateSupplier(idSupplier: string): Observable<void> {
    return this.httpClient.patch<void>(`${environment.back_url}${apiEndpoints.validateSupplierAccount(idSupplier)}`, {});
  }

}
