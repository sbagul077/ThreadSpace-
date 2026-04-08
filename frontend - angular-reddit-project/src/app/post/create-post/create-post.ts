import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators,ReactiveFormsModule  } from '@angular/forms';
import { CreatePostPayload } from './create-post.payload';
import { SubredditModel } from '../../subreddit/subreddit-response';
import { Router } from '@angular/router';
import { PostService } from '../../shared/post.service';
import { Subreddit } from '../../subreddit/subreddit';
import { throwError } from 'rxjs';
import { EditorModule } from '@tinymce/tinymce-angular';

@Component({
  selector: 'app-create-post',
  imports: [ReactiveFormsModule, EditorModule],
  templateUrl: './create-post.html',
  styleUrl: './create-post.css',
})
export class CreatePost implements OnInit{

  createPostForm!: FormGroup;
  postPayload: CreatePostPayload;
  subreddits: Array<SubredditModel> = [];

  constructor(private router: Router, private post: PostService,
    private subredditService: Subreddit){
      this.postPayload = {
        postName: '',
        url: '',
        description: '',
        subredditName: ''
      }
    }

    ngOnInit(){
      this.createPostForm = new FormGroup({
        postName: new FormControl('', Validators.required),
        subredditName: new FormControl('', Validators.required),
        url: new FormControl('', Validators.required),
        description: new FormControl('', Validators.required),
      });

      this.subredditService.getAllSubreddits().subscribe({
        next: (data) => {
          this.subreddits = data;
        },
        error: (error) => {
          console.error('Failed to load subreddits:', error);
        }
      });
    }

    createPost(){
      this.postPayload.postName = this.createPostForm.get('postName')!.value;
      this.postPayload.subredditName = this.createPostForm.get('subredditName')!.value;
      this.postPayload.url = this.createPostForm.get('url')!.value;
      this.postPayload.description = this.createPostForm.get('description')!.value;

      this.post.createPost(this.postPayload).subscribe({
        next: () => {
          this.router.navigateByUrl('/');
        },
        error: (error) => {
          console.error('Create post failed:', error);
        }
      });      
    }
    
    discardPost() {
      this.router.navigateByUrl('/');
    }
}
