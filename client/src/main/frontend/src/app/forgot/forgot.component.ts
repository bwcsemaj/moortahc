import { Component, OnInit } from '@angular/core';
import {FormControl} from "@angular/forms";

@Component({
  selector: 'app-forgot',
  templateUrl: './forgot.component.html',
  styleUrls: ['./forgot.component.css']
})
export class ForgotComponent implements OnInit {
  emailAddressInput: FormControl;

  constructor() { }

  ngOnInit(): void {
    this.emailAddressInput = new FormControl();
  }

  onSendCode() {

  }
}
