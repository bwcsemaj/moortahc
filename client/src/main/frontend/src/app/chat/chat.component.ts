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


  constructor(private messageService: MessageService) {
  }

  ngOnInit(): void {

  }

  onSend() {
    this.messageService.createPost(this.content).then();
  }

  onLogout() {
    this.messageService.logout();
  }
}
