import { Component, OnInit } from '@angular/core';
import { ImageService } from 'src/app/services/image.service';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { TokenStorageService } from 'src/app/services/tokenStorageService';
class ImageSnippet {
  constructor(public src: string, public file: File) {}
}
@Component({
  selector: 'app-upload-media',
  templateUrl: './upload-media.component.html',
  styleUrls: ['./upload-media.component.css']
})


export class UploadMediaComponent implements OnInit {
  public imagePath;
  imgURL: any;
  uploadImageForm :FormGroup;
  constructor(private formBuilder : FormBuilder, 
     private tokenStorageService: TokenStorageService,
     private imageService: ImageService) { 
    this.uploadImageForm = this.formBuilder.group({
      title : new FormControl('', [Validators.required]),
      id : new FormControl(''),
      description : new FormControl('', [Validators.required])
    });

  }
  

  ngOnInit() {
  }


  selectedFile: ImageSnippet;

  preview(files) {
    if (files.length === 0)
      return;
 
    var mimeType = files[0].type;
    if (mimeType.match(/image\/*/) == null) {
      console.log( "Only images are supported.");
      return;
    }
 
    var reader = new FileReader();
    this.imagePath = files;
    reader.readAsDataURL(files[0]); 
    reader.onload = (_event) => { 
      this.imgURL = reader.result; 
    }
  }

  processFile(files) {
    const file: File = files[0];
    const reader = new FileReader();

    reader.addEventListener('load', (event: any) => {

    
    //  this.selectedFile = files[0]

      this.imageService.uploadImage(
              file, 
              this.uploadImageForm.controls['title'].value,
              this.uploadImageForm.controls['description'].value,
              this.tokenStorageService.retrieveFromStorage("USER_ID"))
            .subscribe(
        (res) => {
          this.uploadImageForm.controls['title'].setValue( '' );
          this.uploadImageForm.controls['description'].setValue( '' );
          this.uploadImageForm.controls['id'].setValue( '' );
          this.imgURL="";
        },
        (err) => {
          console.log(err);
        })
    });

    reader.readAsDataURL(file);
  }
}
