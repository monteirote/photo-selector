import { HttpClient } from '@angular/common/http';
import { Injectable, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Imagem } from '../models/imagem';

@Injectable({
  providedIn: 'root'
})
export class BackendService{

  private url = 'http://localhost:8080/api'

  constructor(private http: HttpClient) {}

  getAllImages(): Observable<Imagem[]> {
    return this.http.get<Imagem[]>(`${this.url}/images/all-images`);
  }

  getImageById(id: number) {
    return this.http.get<Imagem>(`${this.url}/images/imagem/${id}`);
  }


}
