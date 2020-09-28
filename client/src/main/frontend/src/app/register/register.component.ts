import {Observable} from "rxjs";
import {Post} from "../post";
import {Component, OnInit} from "@angular/core";
import {Router} from '@angular/router';
import {FormControl} from "@angular/forms";
import {MessageService} from "../message-service";


@Component({
  selector: 'app-chat',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  content: string;

  posts: Post[] = [];
  comments: Comment[] = [];

  constructor(private messageService: MessageService) {
  }

  ngOnInit(): void {
    this.messageService.commentEmitter.subscribe(comment =>{
      this.comments.push(comment);
    });
    this.messageService.postEmitter.subscribe(post =>{
      this.posts.push(post);
    });
  }

  onSend() {
    this.messageService.createPost(this.content).then();
  }

  onLogout() {
    this.posts = [];
    this.comments = [];
    this.messageService.logout();
  }
}
