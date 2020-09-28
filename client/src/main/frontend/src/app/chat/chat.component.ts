import { Observable } from "rxjs";
import { Post } from "../post";
import { Component, OnInit } from "@angular/core";
import { Router } from '@angular/router';



@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit {
  posts: Observable<Post[]>
  constructor() { }

  ngOnInit(): void {
  }

}
