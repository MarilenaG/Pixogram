import { Injectable } from '@angular/core';
// import { Mentor } from '../model/mentor';
import { ApplicationUser } from '../model/applicationUser';
import { HttpClient} from "@angular/common/http";
import { Router,NavigationExtras } from '@angular/router';
import { tap, catchError } from 'rxjs/operators';
import { Observable, of, throwError } from 'rxjs';
import { Time } from '@angular/common';


@Injectable()
export class MentorService {
  constructor(private http: HttpClient, private router : Router) { }
  
  private handleError<T> (operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error); // log to console instead
      return of(result as T);
    };
  } 



  // register(mentor: Mentor): Observable<any> {
  //   return this.http.post('training/signup', mentor);
  // }

  // confirmRegistration(mentor: Mentor): Observable<any> {
  //       return this.http.put('training/confirmRegistration?mentorName='+ mentor.userName+'&registrationCode='+ mentor.registrationCode,null);
  // }

  

  

  // listMentors (): Observable<Mentor[]> {
  //   return this.http.get<Mentor[]>("training/mentor/mentors") ;
  // }

  // getMentor( mentorId ) : Observable<Mentor> {
  //   return this.http.get<Mentor>(`training/mentor/${mentorId}`) ;
  // }
  
  // blockMentor(mentor): Observable<any> {
  //   return this.http.post('training/mentor/blockMentor',mentor);
  // }

  // unblockMentor(mentor): Observable<any> {
  //   return this.http.post('training/mentor/unblockMentor', mentor);
  // }
 
  // findMentorsBySkillAndDate( skillId : number, desiredDate: Date, fromTime:Time, toTime:Time  ) : Observable<Mentor[]> {
  //   const representation = 
  //      {skillId: skillId, desiredDate: desiredDate, fromTime:fromTime,toTime:toTime}    ;
  //   return this.http.post<Mentor[]>('training/mentor/findByTimeAndTechnology', representation) ;
  // }
}