import { Component, OnInit } from '@angular/core';
import { Follower } from 'src/app/model/follower';
import { UserService } from 'src/app/services/user.service';
import { TokenStorageService } from 'src/app/services/tokenStorageService';
import { ImageService } from 'src/app/services/image.service';
import { Image } from 'src/app/model/image';
import { Comment } from 'src/app/model/comment';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';


@Component({
  selector: 'app-following',
  templateUrl: './following.component.html',
  styleUrls: ['./following.component.css']
})
export class FollowingComponent implements OnInit {
  usersFollowedByMe: Follower[];
  imagesBySelectedUser :Image[];
  commentsForSelectedImage :Comment[];
  selectedUserId : String;
  showImageGallery : boolean;
  showCommentsDialog : boolean;
  selectedImageId : number;
  addCommentGroup:FormGroup;
  constructor(private userService:UserService, private tokenStorageService:TokenStorageService,
     private imageService:ImageService, private formBuilder :FormBuilder) { }

  ngOnInit() {
    this.displayList();
    this.usersFollowedByMe=<Follower[]>[];
    this.imagesBySelectedUser=<Image[]>[];
    this.selectedUserId="";
    this.showImageGallery=false;
    this.showCommentsDialog = false;
    this.addCommentGroup = this.formBuilder.group({
      content:  new FormControl('', [Validators.required])
    });
  }

  displayList() {
    this.userService.listUsersFollowedByMe( this.tokenStorageService.retrieveFromStorage("USER_ID"))
    .subscribe(res => {
      this.usersFollowedByMe = res;
      console.log(this.usersFollowedByMe);
    }, err => {
      console.log(err);
    });
    this.showImageGallery=false;
    this.showCommentsDialog = false;
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

  showThisUsersGallery(userId:String){
    this.selectedUserId = userId;
    this.showImageGallery = true;
    this.showCommentsDialog = false;
    this.listImagesBySelectedUser(this.selectedUserId);
  }

  likeImage(imageId:number){
    this.imageService.likeImage(imageId,this.tokenStorageService.retrieveFromStorage("USER_ID") )
    .subscribe(res => {
      this.listImagesBySelectedUser(this.selectedUserId);
    }, err => {
      console.log(err);
    });
  }
  dislikeImage(imageId:number){
    this.imageService.dislikeImage(imageId,this.tokenStorageService.retrieveFromStorage("USER_ID") )
    .subscribe(res => {
      this.listImagesBySelectedUser(this.selectedUserId);
    }, err => {
      console.log(err);
    });
  }
  showComments(imageId){
    this.showCommentsDialog = true;
    this.selectedImageId = imageId;
    this.imageService.listCommentsByImage(imageId )
    .subscribe(res => {
      this.commentsForSelectedImage = res;
    }, err => {
      console.log(err);
    });
  }

  addComent(){
   
    var comment:Comment = new Comment();
    comment.imageId = this.selectedImageId;
    comment.userId = this.tokenStorageService.retrieveFromStorage("USER_ID");
    comment.content = this.addCommentGroup.controls['content'].value;
    this.imageService.addComment(comment )
    .subscribe(res => {
      this.showComments(this.selectedImageId);
    }, err => {
      console.log(err);
    });
  }
  
}
