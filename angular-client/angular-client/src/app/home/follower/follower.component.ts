import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/model/user';
import { UserService } from 'src/app/services/user.service';
import { TokenStorageService } from 'src/app/services/tokenStorageService';

@Component({
  selector: 'app-follower',
  templateUrl: './follower.component.html',
  styleUrls: ['./follower.component.css']
})
export class FollowerComponent implements OnInit {
  users: User[];
  constructor(private userService:UserService, private tokenStorageService:TokenStorageService) { }

  ngOnInit() {
    this.displayList();
  }

  displayList() {
    this.userService.listUsers( this.tokenStorageService.retrieveFromStorage("USER_ID"))
    .subscribe(res => {
      this.users = res;
      console.log(this.users);
    }, err => {
      console.log(err);
    });
  }

  handleChange(event, selectedUserId:string){
    if (event.checked==true) {
      this.followUser (selectedUserId);
    } else {
      this.unfollowUser (selectedUserId);
    }
  }


  followUser(userId:String){
    this.userService.followUser( this.tokenStorageService.retrieveFromStorage("USER_ID"), userId)
    .subscribe(res => {
      this.users = res;
      console.log(this.users);
    }, err => {
      console.log(err);
    });
  }

  unfollowUser(userId:String){
    this.userService.unfollowUser( this.tokenStorageService.retrieveFromStorage("USER_ID"), userId)
    .subscribe(res => {
      this.users = res;
      console.log(this.users);
    }, err => {
      console.log(err);
    });
  }
}
