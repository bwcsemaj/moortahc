import {Component, OnInit} from "@angular/core";
import {FormControl} from "@angular/forms";
import {MessageService} from "../message-service";


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  passwordInput: FormControl;
  emailAddressInput: FormControl;
  firstNameInput: FormControl;
  lastNameInput: FormControl;

  constructor(private messageService: MessageService) {
  }

  ngOnInit(): void {
    this.emailAddressInput = new FormControl();
    this.passwordInput = new FormControl();
    this.firstNameInput = new FormControl();
    this.lastNameInput = new FormControl();
  }

  onSubmit() {
    this.messageService.register(this.emailAddressInput.value,
      this.firstNameInput.value, this.lastNameInput.value, this.passwordInput.value);
  }
}
