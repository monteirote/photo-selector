import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MetadataService {

  private apiUrl = 'http://seu-backend.com/api/metadata'; // Substitua pela URL do seu backend Spring

  constructor(private http: HttpClient) { }

  getMetadataFromUrl(url: string): Observable<any> {
    return this.http.post<any>(this.apiUrl, { url });
  }
}
