import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { PostModel } from '../shared/post-model';
import { PostTile } from "../shared/post-tile/post-tile";
import { SideBar } from "../shared/side-bar/side-bar";
import { SubredditSideBar } from "../shared/subreddit-side-bar/subreddit-side-bar";
import { PostService } from '../shared/post.service';


@Component({
  selector: 'app-home',
  standalone: true,
  imports: [FontAwesomeModule, PostTile, SideBar, SubredditSideBar],
  templateUrl: './home.html',
  styleUrl: './home.css',
})
export class Home implements OnInit{


  posts: Array<PostModel> = [];


  constructor(private post: PostService){ 
    
    this.post.getAllPosts().subscribe(post => {
      this.posts = post;
    });
  }

  ngOnInit(): void {    
  }
}
