import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class MessageService {

  private baseUrl = 'http://localhost:8080';

  constructor(
    private http: HttpClient,
    private router: Router) {
  }

  eventSource: EventSource;
  currentRoomName: string;

  token: string;

  createEventSource(roomName: string): EventSource{
    this.currentRoomName = roomName;
    return new EventSource(`http://localhost:8080/r/listen/${encodeURIComponent(this.currentRoomName)}`);
  }

  connectTo(roomName: string){
    if(this.eventSource != null){
      this.eventSource.close();
    }
    this.eventSource = this.createEventSource(roomName);
    this.eventSource.onmessage = (e) => {
      console.log('connection message');
      console.log(e.data);
    }
    this.eventSource.onerror = (e) => {
      console.log('connection error');
      console.log(e);
      //this.eventSource.close();
    }
    this.eventSource.onopen = (e) => {
      console.log('connection open');
      console.log(e);
    }
  }





  //localhost:8080/c/20?content=HELLO
  createComment(postId: number, content: string): Promise<void> {
    return this.http.post(`${this.baseUrl}/${postId}`, {
      params: {
        content: content
      },
      observe: 'response'
    }).toPromise()
      .then(response => {
        console.log(response);
      })
      .catch(console.log);
  }

  private getHeaders(){
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${this.token}`
    });
  }


  //localhost:8080/p/create?roomName=roomName&content=content
  createPost(content: string): Promise<void> {
    let headers = this.getHeaders();
    console.log(headers.get("Authorization"));
    console.log(this.token);
    console.log(`${this.baseUrl}/p/create?roomName=${this.currentRoomName}&content=${content}`);
    return this.http.post(`${this.baseUrl}/p/create?roomName=${encodeURIComponent(this.currentRoomName)}&content=${content}`,{}, {
      headers: headers,
      observe: 'response'
    }).toPromise()
      .then(response => {
        console.log(response);
      })
      .catch(console.log);
  }

  login(emailAddress: string, password: string) {
    let headers = this.getHeaders();
    let creds = {
      emailAddress: emailAddress,
      password: password
    };
    console.log(`${this.baseUrl}/a`);
    console.log(emailAddress);
    console.log(password);
    this.http.post(`${this.baseUrl}/a/`, creds, {
      headers: headers,
      observe: 'response'
    }).toPromise()
      .then(response => {
        this.token = response.headers.get('Authorization').split(" ")[1];
        console.log(this.token);
        this.router.navigate(['/chat']).then();
        console.log(response);
      })
      .catch(console.log);
  }


}
