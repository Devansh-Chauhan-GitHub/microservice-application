import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { Router } from '@angular/router';

@Injectable({
    providedIn: 'root'
})
export class AuthService {
    private apiUrl = 'http://localhost:8081/auth';
    private tokenKey = 'auth_token';
    private userKey = 'auth_user';

    private currentUserSubject = new BehaviorSubject<any>(this.getUserFromStorage());
    public currentUser$ = this.currentUserSubject.asObservable();

    constructor(private http: HttpClient, private router: Router) { }

    signup(user: any): Observable<any> {
        return this.http.post(`${this.apiUrl}/signup`, user).pipe(
            tap((response: any) => this.handleAuthResponse(response))
        );
    }

    login(credentials: any): Observable<any> {
        return this.http.post(`${this.apiUrl}/login`, credentials).pipe(
            tap((response: any) => this.handleAuthResponse(response))
        );
    }

    logout() {
        localStorage.removeItem(this.tokenKey);
        localStorage.removeItem(this.userKey);
        this.currentUserSubject.next(null);
        this.router.navigate(['/login']);
    }

    getToken(): string | null {
        return localStorage.getItem(this.tokenKey);
    }

    isAuthenticated(): boolean {
        return !!this.getToken();
    }

    private handleAuthResponse(response: any) {
        if (response.token) {
            localStorage.setItem(this.tokenKey, response.token);
            localStorage.setItem(this.userKey, JSON.stringify({
                id: response.userId,
                email: response.email,
                name: response.name
            }));
            this.currentUserSubject.next(response);
        }
    }

    private getUserFromStorage(): any {
        const userStr = localStorage.getItem(this.userKey);
        return userStr ? JSON.parse(userStr) : null;
    }
}
