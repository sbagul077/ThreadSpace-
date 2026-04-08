import { Component, OnInit } from '@angular/core';
import { VoteButton } from "../../shared/vote-button/vote-button";
import { ActivatedRoute, Router, RouterLink } from "@angular/router";
import { SideBar } from "../../shared/side-bar/side-bar";
import { SubredditSideBar } from "../../shared/subreddit-side-bar/subreddit-side-bar";
import { PostModel } from '../../shared/post-model';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { PostService } from '../../shared/post.service';
import { CommentPayLoad } from '../../comment/comment.payload';
import { CommentService } from '../../comment/comment';
import { ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-view-post',
  standalone: true, 
  imports: [VoteButton, RouterLink, SideBar, SubredditSideBar,ReactiveFormsModule],
  templateUrl: './view-post.html',
  styleUrl: './view-post.css',
})
export class ViewPost implements OnInit{
  postId: number;
  post!: PostModel;
  commentForm: FormGroup;
  commentPayLoad: CommentPayLoad;
  comments: CommentPayLoad[] = [];

  constructor(private postService: PostService, private activateRoute: ActivatedRoute,
    private comment: CommentService, private router: Router){
      this.postId= this.activateRoute.snapshot.params['id'];

      this.postService.getPost(this.postId).subscribe({
        next: (data: PostModel) =>{
          this.post = data;
        },
        error: (error: any) => {
          console.error('Error fetching post:', error)
        }
      });

      this.commentForm = new FormGroup({
        text: new FormControl('', Validators.required)
      });

      this.commentPayLoad={
        text:'',
        postId: this.postId
      };
    }

    ngOnInit(): void{
      this.getPostById();
      this.getCommentsForPost();
    }

    postComment(){
      this.commentPayLoad.text= this.commentForm.get('text')?.value;
      this.comment.postComment(this.commentPayLoad).subscribe({
        next: (data: any) =>{
          this.commentForm.get('text')?.setValue('');
          this.getCommentsForPost();
        },
        error: (error: any) => {
          console.error('Error posting comment:',error);
        }
      });
    }


    private getPostById(){
      this.postService.getPost(this.postId).subscribe({
        next: (data: any) =>{
          this.post = data;
        }, 
        error: (error: any) => {
          console.error('Error fetching post by id', error);
        }
      });
    }

    private getCommentsForPost(){
      this.comment.getAllCommentForPost(this.postId).subscribe({
        next: (data: any) => {
          this.comments = data;
        }, error: (error: any) => {
          console.error('Error fetching comment for post', error);
        }
      })


    }
}
