import {Injectable, OnInit, Output, EventEmitter} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {Router} from "@angular/router";
import {Subject} from "rxjs";
import {Post} from "./post";
import {Comment} from "./comment";

@Injectable({
  providedIn: 'root'
})
export class MessageService implements OnInit {

  private baseUrl = 'http://localhost:8080';

  constructor(
    private http: HttpClient,
    private router: Router) {
  }

  ngOnInit(): void {
  }

  eventSource: EventSource;
  currentRoomName: string;

  postEmitter = new EventEmitter<Post>();
  commentEmitter = new EventEmitter<Comment>();

  logoutEmitter = new EventEmitter<Boolean>();

  token: string;

  createEventSource(roomName: string): EventSource {
    this.currentRoomName = roomName;
    return new EventSource(`http://localhost:8080/r/listen?roomName=${encodeURIComponent(this.currentRoomName)}`);
  }

  connectTo(roomName: string) {
    if (this.eventSource != null) {
      this.eventSource.close();
    }
    this.eventSource = this.createEventSource(roomName);
    this.eventSource.onmessage = (e) => {
      let data = e.data;
      //Post will not have a comment id
      if (data.commentId == null) {
        let post = JSON.parse(data);
        post.id = post.postId;
        this.postEmitter.emit(post);
        return;
      }

      //Comments will have post id and comment id
      let comment = JSON.parse(data);
      comment.id = comment.commentId;
      this.commentEmitter.emit(comment);
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

  private getHeaders() {
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${this.token}`
    });
  }


  //localhost:8080/p/create?roomName=roomName&content=content
  createPost(content: string): Promise<void> {
    let headers = this.getHeaders();
    return this.http.post(`${this.baseUrl}/p/create?roomName=${encodeURIComponent(this.currentRoomName)}&content=${content}`, {}, {
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


  logout() {

  }

  //localhost:8080/a/create?emailAddress=account@gmail.com&firstName=Bob&lastName=LastName&password=helloggreaagregrae
  register(emailAddress: string, firstName: string, lastName: string, password: string) {
    let headers = this.getHeaders();
    return this.http.post(`${this.baseUrl}/a/create?emailAddress=${emailAddress}&firstName=${firstName}&lastName=${lastName}&password=${password}`, {}, {
      headers: headers,
      observe: 'response'
    }).toPromise()
      .then(response => {
        this.login(emailAddress, password);
      })
      .catch(console.log);
  }

}
