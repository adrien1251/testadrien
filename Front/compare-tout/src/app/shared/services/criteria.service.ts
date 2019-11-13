import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { apiEndpoints } from './utils/api-endpoints';


@Injectable({
    providedIn: 'root',
})
export class CriteriaService {

    constructor(
        private httpClient: HttpClient,
    ) { }

    env = 'http://18.190.104.245:8080/criteria';

    public getCriterias(idCat: string): Observable<any> {
        return this.httpClient.get<any>(`${this.env}${apiEndpoints.getCriteriaOfCategory(idCat)}`);
    }
}