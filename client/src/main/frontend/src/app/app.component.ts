import {Component, OnInit, ViewChild} from '@angular/core';
import {MessageService} from "./message-service";
import {FormControl} from "@angular/forms";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = "MOORTAHC";

  urlInput: FormControl;



  constructor(private messageService : MessageService) {
  }

  ngOnInit(): void {
    this.urlInput = new FormControl();
    this.urlInput.valueChanges.subscribe(term => {
      console.log('searching for', term);
      // this.messageService.createEventSource(term);
    });
  }

  connect(){
    console.log(this.urlInput.value);
    this.messageService.createEventSource(this.urlInput.value);
  }
}
