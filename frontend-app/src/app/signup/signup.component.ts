import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../auth.service';

@Component({
    selector: 'app-signup',
    standalone: true,
    imports: [CommonModule, FormsModule, RouterLink],
    template: `
    <div class="auth-container">
      <h2>Sign Up</h2>
      <form (ngSubmit)="onSubmit()" #signupForm="ngForm">
        <div class="form-group">
          <label for="name">Name</label>
          <input type="text" id="name" name="name" [(ngModel)]="user.name" required>
        </div>
        <div class="form-group">
          <label for="email">Email</label>
          <input type="email" id="email" name="email" [(ngModel)]="user.email" required>
        </div>
        <div class="form-group">
          <label for="password">Password</label>
          <input type="password" id="password" name="password" [(ngModel)]="user.password" required>
        </div>
        <button type="submit" [disabled]="!signupForm.form.valid">Sign Up</button>
        <p *ngIf="error" class="error">{{ error }}</p>
      </form>
      <p>Already have an account? <a routerLink="/login">Login</a></p>
    </div>
  `,
    styles: [`
    .auth-container { max-width: 400px; margin: 2rem auto; padding: 2rem; border-radius: 8px; box-shadow: 0 4px 6px rgba(0,0,0,0.1); background: white; }
    .form-group { margin-bottom: 1rem; }
    label { display: block; margin-bottom: 0.5rem; }
    input { width: 100%; padding: 0.5rem; border: 1px solid #ddd; border-radius: 4px; }
    button { width: 100%; padding: 0.75rem; background: #28a745; color: white; border: none; border-radius: 4px; cursor: pointer; }
    button:disabled { background: #ccc; }
    .error { color: red; margin-top: 1rem; }
  `]
})
export class SignupComponent {
    user = { name: '', email: '', password: '' };
    error = '';

    constructor(private authService: AuthService, private router: Router) { }

    onSubmit() {
        this.authService.signup(this.user).subscribe({
            next: () => this.router.navigate(['/dashboard']),
            error: (err) => this.error = err.error?.error || 'Signup failed'
        });
    }
}
