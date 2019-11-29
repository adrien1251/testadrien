import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { apiEndpoints } from './utils/api-endpoints';


@Injectable({
    providedIn: 'root',
})
export class CategoryService {
    public currentCategory: any;
    constructor(
        private httpClient: HttpClient,
    ) { }

    env = 'http://localhost:8080/';

    // public getMainCategories(): Observable<any> {
    //     return this.httpClient.get<any>(`${this.env}${apiEndpoints.getMainCategories}`);
    // }

    public getCategories(): Observable<any> {
        return this.httpClient.get<any>(`${this.env}${apiEndpoints.getCategories}`);
    }

    public getCategoriesChild(id?: string): Observable<any> {
        return this.httpClient.get<any>(`${this.env}${apiEndpoints.getCategoriesChild(id)}`);
    }

    public isTopCategory(id?: number): boolean {
        return id === 1 || id === 63 || id === 85 || id === 181;
    }

    public getCurrentCategory(): any {
        return this.currentCategory;
    }

    public setCurrentCategory(cat: any): void {
        this.currentCategory = cat;
    }
}
