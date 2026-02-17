import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, catchError, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor(private http: HttpClient) { }

  getAlpha(): Observable<any> {
    return this.http.get<any>('http://localhost:8081/');
  }

  getBeta(): Observable<any> {
    return this.http.get<any>('http://localhost:8082/');
  }

  getGamma(): Observable<any> {
    return this.http.get<any>('http://localhost:8083/');
  }

  getDelta(): Observable<any> {
    return this.http.get<any>('http://localhost:8084/');
  }

  getEpsilon(): Observable<any> {
    return this.http.get<any>('http://localhost:8085/');
  }
}
