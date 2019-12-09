import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {apiEndpoints} from './utils/api-endpoints';
import {environment} from '../../../environments/environment';
import {Supplier} from '../models/supplier.interface';
import {AuthUtils} from './utils/auth-utils.service';


@Injectable({
  providedIn: 'root',
})
export class SupplierService {

  constructor(private httpClient: HttpClient) { }

  public sendProducts(file: FormData): Observable<void> {
    return null; //TODO : send Product with file
    // return this.httpClient.post<void>(`${environment.back_url}${apiEndpoints.sendProducts(file)}`, {});
  }

  public getSupplier(idSupplier: string): Observable<Supplier> {
    return this.httpClient.get<Supplier>(`${environment.back_url}${apiEndpoints.getSupplier(idSupplier)}`);
  }
}
