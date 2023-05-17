import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/AuthService';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  username: string;
  password: string;
  errorMessage: string;
  successMessage: string;
  invalidLogin: boolean;
  loginSuccess: boolean;

  constructor(private authService: AuthService) {}

  handleLogin() {
    this.authService.login(this.username, this.password).subscribe(
      (result) => {
        console.log('Login successful');
        console.log('Result:', result);
        this.invalidLogin = false;
        this.loginSuccess = true;
        this.successMessage = 'Login Successful';
  
        setTimeout(() => {
          this.loginSuccess = false;
          this.successMessage = '';
        }, 3000); // Hide the success message after 3 seconds
      },
      (error) => {
        console.log('Login failed');
        console.log('Error:', error);
        this.invalidLogin = true;
        this.loginSuccess = false;
        this.errorMessage = error?.error?.message || 'Invalid Credentials';
  
        setTimeout(() => {
          this.invalidLogin = false;
          this.errorMessage = '';
        }, 3000); // Hide the error message after 3 seconds
      }
    );
  }
}