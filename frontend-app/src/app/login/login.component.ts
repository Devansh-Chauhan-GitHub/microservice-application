import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../auth.service';

@Component({
    selector: 'app-login',
    standalone: true,
    imports: [CommonModule, FormsModule, RouterLink],
    template: `
    <div class="auth-container">
      <h2>Login</h2>
      <form (ngSubmit)="onSubmit()" #loginForm="ngForm">
        <div class="form-group">
          <label for="email">Email</label>
          <input type="email" id="email" name="email" [(ngModel)]="credentials.email" required>
        </div>
        <div class="form-group">
          <label for="password">Password</label>
          <input type="password" id="password" name="password" [(ngModel)]="credentials.password" required>
        </div>
        <button type="submit" [disabled]="!loginForm.form.valid">Login</button>
        <p *ngIf="error" class="error">{{ error }}</p>
      </form>
      <p>Don't have an account? <a routerLink="/signup">Sign up</a></p>
    </div>
  `,
    styles: [`
    .auth-container { max-width: 400px; margin: 2rem auto; padding: 2rem; border-radius: 8px; box-shadow: 0 4px 6px rgba(0,0,0,0.1); background: white; }
    .form-group { margin-bottom: 1rem; }
    label { display: block; margin-bottom: 0.5rem; }
    input { width: 100%; padding: 0.5rem; border: 1px solid #ddd; border-radius: 4px; }
    button { width: 100%; padding: 0.75rem; background: #007bff; color: white; border: none; border-radius: 4px; cursor: pointer; }
    button:disabled { background: #ccc; }
    .error { color: red; margin-top: 1rem; }
  `]
})
export class LoginComponent {
    credentials = { email: '', password: '' };
    error = '';

    constructor(private authService: AuthService, private router: Router) { }

    onSubmit() {
        this.authService.login(this.credentials).subscribe({
            next: () => this.router.navigate(['/dashboard']),
            error: (err) => this.error = err.error?.error || 'Login failed'
        });
    }
}
