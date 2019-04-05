import { Component, OnInit } from '@angular/core';
import { Follower } from 'src/app/model/follower';
import { UserService } from 'src/app/services/user.service';
import { TokenStorageService } from 'src/app/services/tokenStorageService';
import { ImageService } from 'src/app/services/image.service';
import { Image } from 'src/app/model/image';

@Component({
  selector: 'app-following',
  templateUrl: './following.component.html',
  styleUrls: ['./following.component.css']
})
export class FollowingComponent implements OnInit {
  usersFollowedByMe: Follower[];
  imagesBySelectedUser :Image[];
  selectedUserId : number;
  constructor(private userService:UserService, private tokenStorageService:TokenStorageService, private imageService:ImageService) { }

  ngOnInit() {
    this.displayList();
    this.usersFollowedByMe=null;
    this.imagesBySelectedUser=null;
    this.selectedUserId=null;
  }

  displayList() {
    this.userService.listUsersFollowedByMe( this.tokenStorageService.retrieveFromStorage("USER_ID"))
    .subscribe(res => {
      this.usersFollowedByMe = res;
      console.log(this.usersFollowedByMe);
    }, err => {
      console.log(err);
    });
  }

  listImagesBySelectedUser(userId : String){
    this.imageService.listImagesByUser(userId)
    .subscribe(res => {
      this.imagesBySelectedUser = res;
      console.log(this.imagesBySelectedUser);
    }, err => {
      console.log(err);
    });
  }
}
