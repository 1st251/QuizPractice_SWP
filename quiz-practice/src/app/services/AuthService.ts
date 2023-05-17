import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders  } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { map } from 'rxjs/operators';
import { catchError } from 'rxjs/operators';


@Injectable({
  providedIn: 'root'
})
export class AuthService {
  
  constructor(private http: HttpClient) {

  }

  login(username: string, password: string) {
    const credentials = { username: username, password: password };
    const headers = new HttpHeaders().set('Cache-Control', 'no-cache');
    
    return this.http.post(environment.hostUrl + '/users/login', credentials, { headers: headers });
  }

  registerSuccessfulLogin(username: string, password: string) {
    sessionStorage.setItem('username', username);
    sessionStorage.setItem('password', password);
  }

  register(user: any) {
    return this.http.post(environment.hostUrl + '/users/register', user);
  }
}