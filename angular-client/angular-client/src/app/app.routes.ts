import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SignupComponent } from './signup/signup.component';
import { ConfirmRegistrationComponent } from './confirm-registration/confirm-registration.component';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { AccountComponent } from './home/account/account.component';
import { UploadMediaComponent } from './home/upload-media/upload-media.component';
import { MyMediaComponent } from './home/my-media/my-media.component';
import { FollowerComponent } from './home/follower/follower.component';
import { FollowingComponent } from './home/following/following.component';


const mainRoutes: Routes = [
  { path: 'signup',
    component: SignupComponent,
  }  ,
  { path: 'confirmRegistration',
    component: ConfirmRegistrationComponent,
  }  ,
  { path: 'login',
    component: LoginComponent,
  } ,
  { path: 'home',
    component: HomeComponent,
    children: [
      { path: 'account', component: AccountComponent },
      { path: 'uploadMedia', component: UploadMediaComponent },
      { path: 'myMedia', component: MyMediaComponent },
      { path: 'following', component: FollowingComponent },
      { path: 'follower', component: FollowerComponent },
      { path: '', redirectTo: 'account', pathMatch: 'full' }
    ]
  } 

 
];


@NgModule({
  declarations: [],
  imports: [
    RouterModule.forRoot(mainRoutes)
  ],

  exports: [
    RouterModule
  ]
})

export class AppRoutesModule { }