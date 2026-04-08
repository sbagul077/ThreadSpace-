import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SubredditSideBar } from './subreddit-side-bar';

describe('SubredditSideBar', () => {
  let component: SubredditSideBar;
  let fixture: ComponentFixture<SubredditSideBar>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SubredditSideBar]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SubredditSideBar);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
