import { Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { Auth } from '../auth/shared/auth';
import { Router } from '@angular/router';
import { faUser } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-header',
  standalone: true, 
  imports: [RouterLink],
  templateUrl: './header.html',
  styleUrl: './header.css',
})
export class Header implements OnInit{
  faUser = faUser;
  isLoggedIn!: boolean;
  username!: string;

  constructor(private auth: Auth, private router: Router){ }

  ngOnInit(): void {
    this.auth.loggedIn.subscribe((data: boolean) => this.isLoggedIn = data);
    this.auth.username.subscribe((data: string) => this.username = data);
    this.isLoggedIn = this.auth.isLoggedIn();
    this.username = this.auth.getUserName();
  }
  logout() {
    this.auth.logout();
    this.isLoggedIn = false;
    this.router.navigateByUrl('');
  }

  goToUserProfile() {
    this.router.navigateByUrl('/user-profile/' + this.username);
  }

}
 