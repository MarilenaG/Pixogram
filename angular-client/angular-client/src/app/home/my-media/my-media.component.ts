import { Component, OnInit } from '@angular/core';
import { Image } from 'src/app/model/image';
import { ImageService } from 'src/app/services/image.service';
import { TokenStorageService } from 'src/app/services/tokenStorageService';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { THIS_EXPR } from '@angular/compiler/src/output/output_ast';

@Component({
  selector: 'app-my-media',
  templateUrl: './my-media.component.html',
  styleUrls: ['./my-media.component.css']
})
export class MyMediaComponent implements OnInit {
  images: Image[];
  selectedImageComments: Comment[];
  showDetails:boolean ;
  selectedImage :Image;
  detailsFormGroup:FormGroup;
  constructor(private imageService: ImageService,   private tokenStorageService: TokenStorageService, private formBuilder:FormBuilder) { 
    this.detailsFormGroup = this.formBuilder.group({
      title : new FormControl('', [Validators.required]),
      description: new FormControl('', [Validators.required])
    });
  }

  ngOnInit() {
    this.displayList();
    this.showDetails =false;
    this.selectedImage=null;
    this.selectedImageComments=null;
  
  }
  displayList() {
    this.imageService.listImagesByUser( this.tokenStorageService.retrieveFromStorage("USER_ID"))
    .subscribe(res => {
      this.images = res;
      console.log(this.images);
    }, err => {
      console.log(err);
    });
  }

  setAsDefaultImage(imageId){
    this.imageService.setAsDefaultImage (imageId,this.tokenStorageService.retrieveFromStorage("USER_ID") )
      .subscribe(res => {
          this.displayList();
      }, err => {
        console.log(err);
    });
  }

  displayDetail(image){
    this.selectedImage = image;
    this.showDetails = true;
    this.detailsFormGroup.controls['title'].setValue( image.title );
    this.detailsFormGroup.controls['description'].setValue( image.description );

    this.imageService.listCommentsByImage(image.id)
    .subscribe(res => {
      this.selectedImageComments = res;
      console.log(this.selectedImageComments);
    }, err => {
      console.log(err);
    });
  }

  updateImageDetails(){
    this.selectedImage.title = this.detailsFormGroup.controls['title'].value;
    this.selectedImage.description = this.detailsFormGroup.controls['description'].value;
    this.imageService.updateImageDetails (this.selectedImage)
      .subscribe(res => {
          alert("image details updated!");
          this.showDetails = false;
      }, err => {
        console.log(err);
    });
  }
}
