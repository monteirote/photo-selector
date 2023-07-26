import { HttpClient } from '@angular/common/http';
import { Injectable, OnInit } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BackendService{

  private url = 'http://localhost:8080/api'

  constructor(private http: HttpClient) {}

  getAllImages(): Observable<any> {
    return this.http.get<any>(`${this.url}/images/all-images`);
  }


}
