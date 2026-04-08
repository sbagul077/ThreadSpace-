import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SubredditModel } from './subreddit-response';
import { Auth } from '../auth/shared/auth';

@Injectable({
  providedIn: 'root',
})
export class Subreddit {

  constructor(private http:HttpClient, private auth: Auth){
  }

  getAllSubreddits(): Observable<Array<SubredditModel>>{
    return this.http.get<Array<SubredditModel>>('http://localhost:8080/api/subreddit');
  }

  // createSubreddit(subredditModel: SubredditModel): Observable<SubredditModel>  {
  //   return this.http.post<SubredditModel>('http://localhost:8080/api/subreddit', subredditModel);
  // }

    createSubreddit(subredditModel: SubredditModel): Observable<any> {
    return this.http.post(
      'http://localhost:8080/api/subreddit',
      subredditModel,
      {
        headers: new HttpHeaders({
          Authorization: 'Bearer ' + this.auth.getJwtToken()
        })
      }
    );
  }
}
