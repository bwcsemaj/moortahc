import {Observable} from "rxjs";
import {Post} from "../post";
import {Component, OnInit} from "@angular/core";
import {Router} from '@angular/router';
import {FormControl} from "@angular/forms";
import {MessageService} from "../message-service";


@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit {

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
}
