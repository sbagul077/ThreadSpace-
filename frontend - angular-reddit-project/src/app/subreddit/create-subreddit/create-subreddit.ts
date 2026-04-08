import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup,ReactiveFormsModule, Validators } from '@angular/forms';
import { SubredditModel } from '../subreddit-response';
import { Router } from '@angular/router';
import { Subreddit } from '../subreddit';

@Component({
  selector: 'app-create-subreddit',
  imports: [ReactiveFormsModule],
  templateUrl: './create-subreddit.html',
  styleUrl: './create-subreddit.css',
})
export class CreateSubreddit implements OnInit{

  createSubredditForm: FormGroup;
  subredditModel: SubredditModel;
  title = new FormControl('');
  description = new FormControl('');

  constructor(private router: Router, private subreddit: Subreddit){
    this.createSubredditForm = new FormGroup({
      title: new FormControl('', Validators.required),
      description: new FormControl('', Validators.required)
    });
    this.subredditModel = {
      name: '',
      description: ''
    }
  }
  ngOnInit(): void {
  }

  discard(){
    this.router.navigateByUrl('/');
  }
  
  createSubreddit(){
    this.subredditModel.name = this.createSubredditForm.get('title')?.value;
    this.subredditModel.description = this.createSubredditForm.get('description')!.value;
    this.subreddit.createSubreddit(this.subredditModel).subscribe({
      next: () => this.router.navigateByUrl('/list-subreddits'),
      error: (error) => console.error(error)
  });
  }

}
