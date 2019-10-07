import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { apiEndpoints } from './utils/api-endpoints';


@Injectable({
    providedIn: 'root',
})
export class TestService {

    user: any;
    users: any;
    constructor(
        private httpClient: HttpClient,
    ) { }

    env = 'http://localhost:8080/';

    public displayBack(): Observable<any> {
        return this.httpClient.get<any>(`${this.env}${apiEndpoints.getUsers}`);
    }
}
