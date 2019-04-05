import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { first } from 'rxjs/operators';

import { UserService } from '../services/user.service';


@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {
  title = 'Signup to the pixogram application';
  registerForm: FormGroup;
  submitted = false;

  constructor(private formBuilder: FormBuilder,
    private router: Router , private userService: UserService
    ) { }

  ngOnInit() {
    this.registerForm = this.formBuilder.group({
     
      userName:  new FormControl('', [Validators.required]),
      email:   new FormControl('', [Validators.required]),
      password: new FormControl('', [Validators.required])

  });
   }
   onClickSubmit(data) {
    // stop here if form is invalid
    console.log('form: ', this.registerForm);
    if (this.registerForm.invalid) {
      alert('please provide needen infos');
      return;
    }
    this.userService.register(this.registerForm.value)
    .pipe(first())
    .subscribe(
        data => {
            alert('Registered. Please check your email and use the registration code in the next screen.');
            this.router.navigate(['/confirmRegistration']);
        },
        error => {
           console.error(error);
           alert(error.message);
        });
   }
  }



