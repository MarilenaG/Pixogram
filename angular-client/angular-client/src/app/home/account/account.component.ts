import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';
import { FormBuilder, FormControl, Validators, FormGroup } from '@angular/forms';
import { User } from 'src/app/model/user';
import { TokenStorageService } from 'src/app/services/tokenStorageService';

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.css']
})
export class AccountComponent implements OnInit {
  userDetailForm :FormGroup;
  userDefaultImageLocation:String;

  constructor(private formBuilder : FormBuilder, private userService: UserService,
     private tokenStorageService: TokenStorageService) { 
     this.userDetailForm = this.formBuilder.group({
      id : new FormControl(''),
      userName : new FormControl('', [Validators.required]),
      password:  new FormControl('', [Validators.required]),
      repeatPassword:  new FormControl('', [Validators.required]),
      email:  new FormControl('', [Validators.required])
    });

  }
 
  ngOnInit() {
    //todo : read this from session storage
    console.log("userId: ", this.tokenStorageService.retrieveFromStorage("USER_ID"));
    this.userService.getUser(this.tokenStorageService.retrieveFromStorage("USER_ID")).subscribe( res => {
      this.displayDetailForm(res);
      this.userDefaultImageLocation=res.imageDownloadPath;
      console.log(res);
    }, err => {
      console.log(err);
    });
  }

  displayDetailForm( user:User){
    this.userDetailForm.controls['id'].setValue(user.id);
    this.userDetailForm.controls['userName'].setValue(user.userName);
    this.userDetailForm.controls['password'].setValue(user.password);
    this.userDetailForm.controls['repeatPassword'].setValue(user.password);
    this.userDetailForm.controls['email'].setValue(user.email);
  }

  updateUser(){
    if (this.userDetailForm.invalid) {
      alert('please fill up all the fields');
      return;
    }
    if (this.userDetailForm.controls['password'].value!=
      this.userDetailForm.controls['repeatPassword'].value){
      alert('Password does not match Repeat Password');
      return;
    }
    this.userService.register (this.userDetailForm.value)
      .subscribe(res => {
          alert("user details updated!");
        
      }, err => {
        console.log(err);
    });
  }

}
