import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { apiEndpoints } from './utils/api-endpoints';
import { environment } from '../../../environments/environment';


@Injectable({
    providedIn: 'root',
})
export class AuthService {

    constructor(private httpClient: HttpClient) { }

    public login(email: string, password: string): Observable<any> {
        return this.httpClient.post<any>(`${environment.back_url}${apiEndpoints.login}`, {email, password});
    }
}
