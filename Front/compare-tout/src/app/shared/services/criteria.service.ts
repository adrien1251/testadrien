import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { apiEndpoints } from './utils/api-endpoints';
import {environment} from '../../../environments/environment';


@Injectable({
    providedIn: 'root',
})
export class CriteriaService {

    constructor(
        private httpClient: HttpClient,
    ) { }

    env = environment.back_url;

    public getCriterias(idCat: string): Observable<any> {
        return this.httpClient.get<any>(`${this.env}${apiEndpoints.getCriteriaOfCategory(idCat)}`);
    }

    public getCriteriasValues(idCat: string): Observable<any> {
        return this.httpClient.get<any>(`${this.env}${apiEndpoints.getCriteriaValuesOfCategory(idCat)}`);
    }
}
