import { Injectable } from '@angular/core';
import { HttpClient} from "@angular/common/http";
import { Observable } from 'rxjs';
import { Image } from '../model/image';

@Injectable({
  providedIn: 'root'
})
export class ImageService {


  constructor(private http: HttpClient) {}


  public uploadImage(image: File, title:String, description:String, userId:String): Observable<any> {
    const formData = new FormData();

    formData.append('file', image);

    return this.http.post('/pixogram/image/uploadImage?title='+title+'&description='+description+'&userId='+userId, formData);
   
  }


  public listImagesByUser (userId:String) : Observable<Image[]>{
    return this.http.get<Image[]>("/pixogram/image/getAllImagesForUser?userId="+userId);
  }
  

  public setAsDefaultImage(imageId:number, userId:String) : Observable<any> {
    return this.http.post<Image>('/pixogram/image/setDefaultImage?imageId='+ imageId+"&userId="+userId ,null);

  }

  public updateImageDetails( image:Image): Observable<any> {
 
    return this.http.post('pixogram/image/updateImage', image);
  }


  public listCommentsByImage (imageId:Number): Observable<Comment[]> {
    return this.http.post<Comment[]>("/pixogram/image/showAllCommentsOnImage", imageId);
  }

}