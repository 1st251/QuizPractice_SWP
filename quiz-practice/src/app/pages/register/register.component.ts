import { Component } from '@angular/core';
import { AuthService } from '../../services/AuthService';


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  model = {
    username: '',
    password: '',
    displayName: '',
    email: '',
    dob: ''
  };
  successMessage = '';
  errorMessage = '';
  constructor(private authService: AuthService) {}

  onSubmit() {
    this.authService.register(this.model).subscribe(
      (response: any) => {
        console.log('User registered successfully: ', response.message);
        this.successMessage = 'User registered successfully!';
        this.errorMessage = '';
      },
      error => {
        console.error('Error registering user: ', error);
        this.successMessage = '';
        this.errorMessage = 'Error registering user: ' + error.error;
      }
    );
  }
}
