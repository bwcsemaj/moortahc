import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { ForgotComponent } from './forgot/forgot.component';
import { ChatComponent } from './chat/chat.component';
import { ConfirmCodeComponent } from './confirm-code/confirm-code.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    ForgotComponent,
    ChatComponent,
    ConfirmCodeComponent
  ],
  imports: [
    BrowserModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
