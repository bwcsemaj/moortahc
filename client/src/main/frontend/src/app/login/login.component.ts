import { Component, OnInit } from '@angular/core';
import {FormControl} from "@angular/forms";
import {MessageService} from "../message-service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  userNameInput: FormControl;
  passwordInput: FormControl;

  constructor(private messageService : MessageService) {
  }

  ngOnInit(): void {
    this.userNameInput = new FormControl();
    this.passwordInput = new FormControl();
  }

  onSubmit() {
    this.messageService.login(this.userNameInput.value, this.passwordInput.value);
  }
}
