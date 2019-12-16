import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { apiEndpoints } from './utils/api-endpoints';
import { environment } from '../../../environments/environment';


@Injectable({
    providedIn: 'root',
})
export class CriteriaService {

    public currentFilters: any;
    constructor(
        private httpClient: HttpClient,
    ) { }

    public getCriterias(idCat: string): Observable<any> {
        return this.httpClient.get<any>(`${environment.back_url}${apiEndpoints.getCriteriaOfCategory(idCat)}`);
    }

    public getCriteriasValues(idCat: string): Observable<any> {
        return this.httpClient.get<any>(`${environment.back_url}${apiEndpoints.getCriteriaValuesOfCategory(idCat)}`);
    }


    public getCurrentFilters(): any {
        return this.currentFilters;
    }

    public setCurrentFilters(cat: any): void {
        this.currentFilters = cat;
    }
}
