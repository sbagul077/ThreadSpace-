import { Component, OnInit } from '@angular/core';
import { PostModel } from '../../shared/post-model';
import { CommentPayLoad } from '../../comment/comment.payload';
import { ActivatedRoute } from '@angular/router';
import { PostService } from '../../shared/post.service';
import { CommentService } from '../../comment/comment';
import { PostTile } from "../../shared/post-tile/post-tile";

@Component({
  selector: 'app-user-profile',
  imports: [PostTile],
  templateUrl: './user-profile.html',
  styleUrl: './user-profile.css',
})
export class UserProfile implements OnInit{
  name: string;
  posts!: PostModel[];
  comments!: CommentPayLoad[];
  postLength!: number;
  commentLength!: number;

  constructor(private activatedRoute: ActivatedRoute, private post: PostService,
    private comment: CommentService){
      this.name = this.activatedRoute.snapshot.params['name'];

      this.post.getAllPostsByUser(this.name).subscribe(data => {
        this.posts = data;
        this.postLength = data.length;
      });
      
      this.comment.getAllCommentsByUser(this.name).subscribe(data => {
        this.comments = data;
        this.commentLength = data.length;
      });
    }

    ngOnInit(): void { 
    }
}
