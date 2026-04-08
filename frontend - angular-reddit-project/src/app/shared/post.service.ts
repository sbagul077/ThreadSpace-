import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PostModel } from './post-model';
import { CreatePostPayload } from '../post/create-post/create-post.payload';
import { Auth } from '../auth/shared/auth';

@Injectable({
  providedIn: 'root',
})
export class PostService {


  constructor(private http: HttpClient,private auth: Auth){}
  
  getAllPosts(): Observable<Array<PostModel>> {
    return this.http.get<Array<PostModel>>('http://localhost:8080/api/posts');
  }

  // createPost(postPayload: CreatePostPayload): Observable<any> {
  //     const token = this.auth.getJwtToken();

  //   return this.http.post(
  //     'http://localhost:8080/api/posts',
  //     postPayload,
  //     {
  //       headers: new HttpHeaders({
  //         Authorization: `Bearer ${token}`
  //       })
  //     }
  //   );
  // }

  createPost(postPayload: CreatePostPayload): Observable<any> {
    return this.http.post( 'http://localhost:8080/api/posts', postPayload);
  }
  getPost(id: number): Observable<PostModel>{
    return this.http.get<PostModel>('http://localhost:8080/api/posts' + id);
  }

  getAllPostsByUser(name: string): Observable<PostModel[]>{
    return this.http.get<PostModel[]>('http://localhost:8080/api/comments/by-user/' + name);
  }
}
