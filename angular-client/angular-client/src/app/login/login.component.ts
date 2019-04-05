import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup} from '@angular/forms';
import {Router} from '@angular/router';
import { first } from 'rxjs/operators';

import { UserService } from '../services/user.service';
import { TokenStorageService} from '../services/tokenStorageService';
import { Validators } from '@angular/forms'
import { AppComponent } from '../app.component';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
private loginForm: FormGroup;

  constructor(private formBuilder: FormBuilder,
    private router: Router , private userService: UserService,
    private tokenStorageService: TokenStorageService ) { }

    ngOnInit() {
      this.loginForm = this.formBuilder.group({
        userName:  new FormControl('', [Validators.required]),
        password: new FormControl('', [Validators.required])
      });
     }

onClickSubmit(data) {
  // stop here if form is invalid
  console.log('form: ', this.loginForm);
  if (this.loginForm.invalid) {
    alert('please provide username and password');
    return;
  }
  this.userService.login(this.loginForm.value)
  .pipe(first())
  .subscribe(
      data => {
          console.log('Confirmed!',data);
          this.tokenStorageService.setLoginUserDetails(data);
         
          this.router.navigate(['/home']);
         
          console.log(this.tokenStorageService.retrieveFromStorage("JWT_TOKEN"));
      },
      
      error => {
         console.error(error);
         alert(error.message);
      });
 }
}

