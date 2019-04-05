import { Injectable } from "@angular/core";



@Injectable()
export class TokenStorageService {


    setLoginUserDetails(user: any) {
        console.log("setting in storage"+  user.userName +"/" + user.id +"/" + user.jwtHeader);
        localStorage.setItem('USER_NAME', user.userName);
        localStorage.setItem('USER_ID', user.id);
        localStorage.setItem('JWT_TOKEN',  user.jwtHeader );
    }

    retrieveFromStorage(key:string){
        return localStorage.getItem(key);
    }

    
}