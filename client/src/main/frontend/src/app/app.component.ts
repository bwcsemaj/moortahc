import {Component, OnInit, ViewChild} from '@angular/core';
import {MessageService} from "./message-service";
import {FormControl} from "@angular/forms";
import {debounceTime, distinctUntilChanged} from "rxjs/operators";
import {Post} from "./post";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent implements OnInit {
  title = "MOORTAHC";

  urlInput: FormControl;
  posts: Post[] = [];
  comments: Comment[] = [];

  MAX_SIZE = 50;


  constructor(private messageService : MessageService) {
  }

  ngOnInit(): void {
    this.urlInput = new FormControl();
    this.urlInput.valueChanges
      .pipe(
        debounceTime(400),
        distinctUntilChanged()
      ).subscribe(res=> {
        this.connect()
      });
    this.messageService.logoutEmitter.subscribe(value =>{
      this.urlInput.setValue(null);
      this.posts = [];
      this.comments = [];
    });

    this.messageService.commentEmitter.subscribe(comment =>{
      this.comments.push(comment);
      if(this.comments.length > this.MAX_SIZE){
        this.comments.pop();
      }
      const element = document.createElement('li');
      element.innerHTML = `${comment.fromId}:${comment.content}`;
      document.getElementById('chat-messages').appendChild(element);
    });

    this.messageService.postEmitter.subscribe(post =>{
      this.posts.push(post);
      if(this.posts.length > this.MAX_SIZE){
        this.posts.pop();
      }
      const element = document.createElement('li');
      element.innerHTML = `${post.fromId}:${post.content}`;
      document.getElementById('chat-messages').appendChild(element);
    });
  }

  connect(){
    console.log(this.urlInput.value);
    this.messageService.connectTo(this.urlInput.value);
  }


}
