import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class MessageService {

  private baseUrl = 'http://localhost:8080/';

  constructor(private http: HttpClient) {
  }

  eventSource: EventSource;

  createEventSource(url: string): EventSource{
    return new EventSource(url);
  }

  connectTo(url: string){
    if(this.eventSource != null){
      this.eventSource.close();
    }

    this.eventSource = this.createEventSource(url);
    this.eventSource.onmessage = (e) => {
      console.log('connection message');
      console.log(e.data);
    }
    this.eventSource.onerror = (e) => {
      console.log('connection error');
      console.log(e);
      this.eventSource.close();
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


  //localhost:8080/p/create?roomName=roomName&content=content
  createPost(roomName: string, content: string): Promise<void> {
    return this.http.post(`${this.baseUrl}/p/create`, {
      params: {
        roomName: roomName,
        content: content
      },
      observe: 'response'
    }).toPromise()
      .then(response => {
        console.log(response);
      })
      .catch(console.log);
  }

}
