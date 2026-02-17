import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';

@Component({
    selector: 'app-dashboard',
    standalone: true,
    imports: [CommonModule],
    templateUrl: './dashboard.component.html',
    styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

    userProfile: any = null;
    orders: any[] = [];
    payments: any[] = [];
    notifications: any[] = [];

    constructor(
        private http: HttpClient,
        private cdr: ChangeDetectorRef,
        private authService: AuthService,
        private router: Router
    ) { }

    ngOnInit(): void {
        this.fetchUserProfile();
        this.fetchOrders();
        this.fetchPayments();
        this.fetchNotifications();
    }

    fetchUserProfile() {
        this.http.get('http://localhost:8082/users/me').subscribe({
            next: (data) => {
                this.userProfile = data;
                this.cdr.detectChanges();
            },
            error: (err) => console.error('Error fetching profile', err)
        });
    }

    fetchOrders() {
        this.http.get<any[]>('http://localhost:8083/orders').subscribe({
            next: (data) => {
                this.orders = data;
                this.cdr.detectChanges();
            },
            error: (err) => console.error('Error fetching orders', err)
        });
    }

    fetchPayments() {
        this.http.get<any[]>('http://localhost:8084/payments').subscribe({
            next: (data) => {
                this.payments = data;
                this.cdr.detectChanges();
            },
            error: (err) => console.error('Error fetching payments', err)
        });
    }

    fetchNotifications() {
        this.http.get<any[]>('http://localhost:8085/notifications').subscribe({
            next: (data) => {
                this.notifications = data;
                this.cdr.detectChanges();
            },
            error: (err) => console.error('Error fetching notifications', err)
        });
    }

    createOrder() {
        const order = { item: 'New Item', quantity: 1, totalPrice: 100.00 };
        this.http.post('http://localhost:8083/orders', order).subscribe({
            next: () => this.fetchOrders(),
            error: (err) => console.error('Error creating order', err)
        });
    }

    makePayment() {
        // Find first pending order to pay for demo purposes
        const pendingOrder = this.orders.find(o => o.status === 'PENDING');
        if (pendingOrder) {
            const payment = { orderId: pendingOrder.id, amount: pendingOrder.totalPrice, method: 'CREDIT_CARD' };
            this.http.post('http://localhost:8084/payments', payment).subscribe({
                next: () => this.fetchPayments(),
                error: (err) => console.error('Error making payment', err)
            });
        }
    }

    sendNotification() {
        const notification = { type: 'EMAIL', recipient: this.userProfile?.email, message: 'Hello from Dashboard!' };
        this.http.post('http://localhost:8085/notifications', notification).subscribe({
            next: () => this.fetchNotifications(),
            error: (err) => console.error('Error sending notification', err)
        });
    }

    logout() {
        this.authService.logout();
    }
}
