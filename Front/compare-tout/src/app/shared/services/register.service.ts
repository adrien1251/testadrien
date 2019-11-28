import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { apiEndpoints } from './utils/api-endpoints';
import { environment } from '../../../environments/environment';
import {Supplier} from '../models/supplier.interface';
import {Customer} from '../models/customer.interface';


@Injectable({
    providedIn: 'root',
})
export class RegisterService {

    constructor(private httpClient: HttpClient) { }

    public registerSupplier(supplier: Supplier): Observable<any> {
        return this.httpClient.post<any>(`${environment.back_url}${apiEndpoints.registerSupplier}`, supplier);
    }

    public registerCustomer(supplier: Customer): Observable<any> {
        console.log('cc');
        return this.httpClient.post<any>(`${environment.back_url}${apiEndpoints.registerCustomer}`, supplier);
    }
}
