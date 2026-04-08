import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { SignupRequestPayload } from './signup-request.payload';
import { Auth } from '../shared/auth';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
// import { SignupRequestPayload } from '../shared/models/signup-request.payload';

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './signup.html',
  styleUrls: ['./signup.css'],
})
export class Signup implements OnInit{
  
  signUpRequestPayload:SignupRequestPayload;
  signupForm!: FormGroup;

  constructor(private auth: Auth, private router: Router,
    private toastr: ToastrService) { 
    this.signUpRequestPayload={
      username:'',
      email:'',
      password:''
    }
  }

  ngOnInit(){
    this.signupForm = new FormGroup({
      username: new FormControl('', Validators.required),
      email: new FormControl('',[Validators.required, Validators.email]),
      password: new FormControl('', Validators.required)
    });
  }

  signup(){

    this.signUpRequestPayload.username = this.signupForm.get('username')?.value;
    this.signUpRequestPayload.email = this.signupForm.get('email')?.value;
    this.signUpRequestPayload.password = this.signupForm.get('password')?.value;
    
    this.auth.signup(this.signUpRequestPayload)
      .subscribe({
        next: (data) => {
          this.router.navigate(['/login'],
            { queryParams: { registered: 'true' } }
          );
        },
        error: (error) => {
          console.log(error);
          this.toastr.error('Registration Failed! Please try again');
        }
      });
  }
}
