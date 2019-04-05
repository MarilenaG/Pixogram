import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { first } from 'rxjs/operators';

import { UserService } from '../services/user.service';

@Component({
  selector: 'app-confirm-registration',
  templateUrl: './confirm-registration.component.html',
  styleUrls: ['./confirm-registration.component.css']
})
export class ConfirmRegistrationComponent implements OnInit {
  confirmRegistrationForm: FormGroup;
  constructor(private formBuilder: FormBuilder,
    private router: Router , private userService: UserService
    ) { }

  ngOnInit() {
    this.confirmRegistrationForm = this.formBuilder.group({
      userName:  new FormControl('', [Validators.required]),
      registrationCode: new FormControl('', [Validators.required, Validators.minLength(6)])
  });
   }
 onClickSubmit(data) {
  // stop here if form is invalid
  console.log('form: ', this.confirmRegistrationForm);
  if (this.confirmRegistrationForm.invalid) {
    alert('please provide needen infos');
    return;
  }
  this.userService.confirmRegistration(this.confirmRegistrationForm.value)
  .pipe(first())
  .subscribe(
      data => {
          alert('Confirmed!');
          this.router.navigate(['/login']);
      },
      error => {
         console.error(error);
         alert(error.message);
      });
 }
}
