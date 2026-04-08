import { Component } from '@angular/core';
import { RouterLink } from "@angular/router";
import { Subreddit } from '../subreddit';
import { throwError } from 'rxjs';
import { SubredditModel } from '../subreddit-response';
import { error } from 'console';
import { SideBar } from "../../shared/side-bar/side-bar";

@Component({
  selector: 'app-list-subreddits',
   standalone: true,
  imports: [RouterLink, SideBar],
  templateUrl: './list-subreddits.html',
  styleUrl: './list-subreddits.css',
})

export class ListSubreddits {

  subreddits: Array<SubredditModel> = [];
  constructor(private subreddit: Subreddit){}

  ngOnInit(){
    // console.log('ListSubreddits ngOnInit fired');
    this.subreddit.getAllSubreddits().subscribe({
      next: (data) =>{
        console.log('subreddits loaded in browser:', data);
        this.subreddits = data;
      } ,
      error: (error) => {
        console.error(error);
      }
    });
  }
}
