import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { apiEndpoints } from './utils/api-endpoints';


@Injectable({
    providedIn: 'root',
})
export class CategoryService {

    constructor(
        private httpClient: HttpClient,
    ) { }

    env = 'http://http://18.190.29.156:8080';

    public getMainCategories(): Observable<any> {
        return this.httpClient.get<any>(`${this.env}${apiEndpoints.getMainCategories}`);
    }

    public getChildCategories(id: string): Observable<any> {
        return this.httpClient.get<any>(`${this.env}${apiEndpoints.getChildCategories(id)}`);
    }
}
