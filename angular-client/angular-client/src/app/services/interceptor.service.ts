import {Injectable} from '@angular/core';
import {HttpEvent, HttpInterceptor, HttpHandler, HttpRequest} from '@angular/common/http';
import {Observable} from "rxjs";
import { TokenStorageService } from './tokenStorageService';

@Injectable()
export class Interceptor implements HttpInterceptor {
  constructor(private tokenStorageService: TokenStorageService){

  }
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const jwt_token = this.tokenStorageService.retrieveFromStorage("JWT_TOKEN");
    req = req.clone({
      setHeaders: {
        Authorization: `Bearer ${jwt_token}`
      }
    });

    return next.handle(req);
  }
}