import { Component, Input, OnInit } from '@angular/core';
import { PostModel } from '../post-model';
import { faArrowDown, faArrowUp} from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeModule, FaIconComponent } from '@fortawesome/angular-fontawesome';
import { VotePayLoad } from './vote-payload';
import { VoteService } from '../vote';
import { Auth } from '../../auth/shared/auth';
import { PostService } from '../post.service';
import { ToastrService } from 'ngx-toastr';
import { VoteType } from './vote-type';


@Component({
  selector: 'app-vote-button',
  standalone: true, 
  imports: [FontAwesomeModule],
  templateUrl: './vote-button.html',
  styleUrl: './vote-button.css',
})
export class VoteButton implements OnInit{

  @Input() post!: PostModel;
  votePayLoad: VotePayLoad;
  faArrowUp = faArrowUp;
  faArrowDown = faArrowDown;
  upvoteColor : string = '';
  downvoteColor : string = '';
  isLoggedIn!: boolean;

  constructor(private voteService: VoteService,
    private auth: Auth,
    private postService: PostService,
    private toaster: ToastrService){
      
      this.votePayLoad = {
        voteType: undefined,
        postId: undefined
      }
      this.auth.loggedIn.subscribe((data: boolean) => this.isLoggedIn = data);
    }

  ngOnInit(): void {
    this.updateVoteDetails();    
  }

  upvotePost() {
    this.votePayLoad.voteType = VoteType.UPVOTE
    this.vote();
    this.downvoteColor = '';
  }

  downvotePost() {
    this.votePayLoad.voteType = VoteType.DOWNVOTE;
    this.vote();
    this.upvoteColor = '';
  }

private vote() {
  this.votePayLoad.postId = this.post.id;

  this.voteService.vote(this.votePayLoad).subscribe({
    next: () => {
      this.updateVoteDetails();
    },
    error: (error: any) => {
      this.toaster.error(error.error.message);
    }
  });
}

  private updateVoteDetails(){
    this.postService.getPost(this.post.id).subscribe(post => {
      this.post = post;
    });
  }
  
}
