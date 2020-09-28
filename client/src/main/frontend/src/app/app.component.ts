import {Component, OnInit, ViewChild} from '@angular/core';
import {MessageService} from "./message-service";
import {FormControl} from "@angular/forms";
import {debounceTime, distinctUntilChanged} from "rxjs/operators";

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
    this.urlInput.valueChanges
      .pipe(
        debounceTime(400),
        distinctUntilChanged()
      ).subscribe(res=> {
        this.connect()
      });
    this.messageService.logoutEmitter.subscribe(value =>{
      this.urlInput.setValue(null);
    });
  }

  connect(){
    console.log(this.urlInput.value);
    this.messageService.connectTo(this.urlInput.value);
  }
}
