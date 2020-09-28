import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {LoginComponent} from './login/login.component';
import {ForgotComponent} from './forgot/forgot.component';
import {ChatComponent} from './chat/chat.component';
import {ConfirmCodeComponent} from './confirm-code/confirm-code.component';
import {HomeComponent} from './home/home.component';
import {RouterModule, Routes} from "@angular/router";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";

const routes: Routes = [
  { path: 'chat', component: ChatComponent },
  { path: 'confirm-code', component: ConfirmCodeComponent },
  { path: 'forgot', component: ForgotComponent },
  { path: '', component: HomeComponent },
];

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    ForgotComponent,
    ChatComponent,
    ConfirmCodeComponent,
    HomeComponent,
  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot(routes),
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
