import { Injectable } from '@angular/core';
import { User } from '../model/user';
import { ApplicationUser } from '../model/applicationUser';
import { HttpClient} from "@angular/common/http";
import { Router,NavigationExtras } from '@angular/router';
import { tap, catchError } from 'rxjs/operators';
import { Observable, of, throwError } from 'rxjs';
import { Follower } from '../model/follower';


@Injectable()
export class UserService {
  constructor(private http: HttpClient, private router : Router) { }
  
  private handleError<T> (operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error); // log to console instead
      return of(result as T);
    };
  } 



  register(user: User): Observable<any> {
    return this.http.post('pixogram/user/signup', user);
  }

  updateUser(user: User): Observable<any> {
    return this.http.post('pixogram/user/updateUser', user);
  }
  confirmRegistration(user: User): Observable<any> {
        return this.http.post('pixogram/user/confirmRegistration?userName='+ user.userName+'&registrationCode='+ user.registrationCode,null);
  }

  login(applicationUser: ApplicationUser): Observable<any> {
    return this.http.post('pixogram/login', applicationUser);
  }

  

  listUsers ( userId :string): Observable<User[]> {
    return this.http.get<User[]>("pixogram/user/"+userId+"/users") ;
  }


  listUsersFollowedByMe ( userId :string): Observable<Follower[]> {
    return this.http.get<Follower[]>("pixogram/user/"+userId+"/following") ;
  }

  getUser( userId :string) : Observable<User> {
    return this.http.get<User>(`pixogram/user/${userId}`) ;
  }
  
  followUser(myUserId:String, userIdToFollow:String): Observable<any> {
    return this.http.post(`pixogram/user/followUser?myUserId=${myUserId}&userIdToFollow=${userIdToFollow}`,null);
  }
  unfollowUser(myUserId:String, userIdToUnfollow:String): Observable<any> {
    return this.http.post(`pixogram/user/unfollowUser?myUserId=${myUserId}&userIdToUnfollow=${userIdToUnfollow}`,null);
  }
 
}