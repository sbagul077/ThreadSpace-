import { Component, Input, OnInit } from '@angular/core';
import { PostService } from '../post.service';
import { PostModel } from '../post-model';
import { faComments } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeModule, FaIconComponent } from '@fortawesome/angular-fontawesome';
import { Router, RouterLink } from '@angular/router';
import { VoteButton } from "../vote-button/vote-button";

@Component({
  selector: 'app-post-tile',
  standalone: true,
  imports: [FaIconComponent, RouterLink, VoteButton],
  templateUrl: './post-tile.html',
  styleUrl: './post-tile.css',
})
export class PostTile implements OnInit {

  @Input() posts: Array<PostModel> = [];

  faComments = faComments;

  constructor(private router: Router){
  }

  ngOnInit(): void { }

  goToPost(id: number): void {
    this.router.navigateByUrl('/view-post/' + id);
  }
}
