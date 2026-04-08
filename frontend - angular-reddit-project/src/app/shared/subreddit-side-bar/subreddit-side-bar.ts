import { Component, OnInit } from '@angular/core';
import { RouterLink } from "@angular/router";
import { Subreddit } from '../../subreddit/subreddit';
import { SubredditModel } from '../../subreddit/subreddit-response';

@Component({
  selector: 'app-subreddit-side-bar',
  standalone: true, 
  imports: [RouterLink],
  templateUrl: './subreddit-side-bar.html',
  styleUrl: './subreddit-side-bar.css',
})
export class SubredditSideBar implements OnInit{

  subreddits: Array<SubredditModel> = [];
  displayViewAll: boolean = false;
;
  
  constructor(private subreddit :Subreddit){
    this.subreddit.getAllSubreddits().subscribe(data =>{
      if(data.length >= 4){
        this.subreddits = data.splice(0, 3);
        this.displayViewAll = true;
      }else{
        this.subreddits = data;
      }
    });
  }

  ngOnInit(): void { }
}
