import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule , ReactiveFormsModule} from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { UserService } from './services/user.service';
import { AppRoutesModule } from './app.routes'
import { TokenStorageService } from './services/tokenStorageService';

import { AppComponent } from './app.component';

import { SignupComponent } from './signup/signup.component';
import { ConfirmRegistrationComponent } from './confirm-registration/confirm-registration.component';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';

import {TabMenuModule} from 'primeng/tabmenu';
import {DataTableModule} from 'primeng/datatable';


import {DropdownModule} from 'primeng/dropdown';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {CalendarModule} from 'primeng/calendar';
import {DialogModule} from 'primeng/dialog';
import { ButtonModule } from 'primeng/components/button/button';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { Interceptor } from './services/interceptor.service';
import { AccountComponent } from './home/account/account.component';
import { UploadMediaComponent } from './home/upload-media/upload-media.component';
import { ImageService } from './services/image.service';
import { MyMediaComponent } from './home/my-media/my-media.component';
import {CardModule} from 'primeng/card';
import { FollowerComponent } from './home/follower/follower.component';
import { FollowingComponent } from './home/following/following.component';
import {InputSwitchModule} from 'primeng/inputswitch';

@NgModule({
  declarations: [
    AppComponent,
    SignupComponent,
    ConfirmRegistrationComponent,
    LoginComponent,
    HomeComponent,
    AccountComponent,
    UploadMediaComponent,
    MyMediaComponent,
    FollowerComponent,
    FollowingComponent
  
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AppRoutesModule,
    ReactiveFormsModule,
    TabMenuModule,
    DataTableModule,
    DropdownModule,
    BrowserAnimationsModule,
    CalendarModule,
    DialogModule,
    ButtonModule,
    CardModule,
    InputSwitchModule
  ],
  
  providers: [
              UserService,
              TokenStorageService,
              ImageService,
              
              {provide: HTTP_INTERCEPTORS,   useClass: Interceptor,  multi: true,}
              ],
  bootstrap: [AppComponent]
})
export class AppModule { 
 
}






