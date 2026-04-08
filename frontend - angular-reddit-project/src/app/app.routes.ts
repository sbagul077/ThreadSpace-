import { Routes } from '@angular/router';
import { Signup } from './auth/signup/signup';
import { Login } from './auth/login/login';
import { Home } from './home/home';
import { CreatePost } from './post/create-post/create-post';
import { CreateSubreddit } from './subreddit/create-subreddit/create-subreddit';
import { ListSubreddits } from './subreddit/list-subreddits/list-subreddits';
import { AuthGuard } from './auth/auth.guard';
import { ViewPost } from './post/view-post/view-post';
import { UserProfile } from './auth/user-profile/user-profile';

export const routes: Routes = [
  {path:'', component: Home},
  {path: 'view-post/:id', component: ViewPost},
  {path: 'user-profile/:name', component: UserProfile, canActivate: [AuthGuard] },
  {path:'list-subreddits', component: ListSubreddits},
  {path:'create-post', component: CreatePost, canActivate: [AuthGuard]},
  {path:'create-subreddit', component: CreateSubreddit},
  {path: 'sign-up', component: Signup },
  {path:'login', component: Login}  
];
