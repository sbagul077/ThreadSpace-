import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CommentPayLoad } from './comment.payload';

@Injectable({
  providedIn: 'root',
})
export class CommentService {
  
  constructor(private httpClient: HttpClient){}

  getAllCommentForPost(postId : number): Observable<CommentPayLoad[]>{
    return this.httpClient.get<CommentPayLoad[]>('http://localhost:8080/api/comments/by-post/' + postId);
  }

  postComment(commentPayLoad: CommentPayLoad): Observable<any> {
    return this.httpClient.post<any>('http://localhost:8080/api/comments', commentPayLoad);
  }

  getAllCommentsByUser(name: string){
    return this.httpClient.get<CommentPayLoad[]>('http://localhost:8080/api/comments/by-user/' + name);
  }

}

