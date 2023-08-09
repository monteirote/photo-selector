import { HttpClient, HttpErrorResponse, HttpResponseBase } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, map, of, throwError } from 'rxjs';
import { Imagem } from '../models/imagem';
import { ImagemDto } from '../models/imagemDto';

@Injectable({
  providedIn: 'root'
})
export class BackendService{

  private urlAPI = 'http://localhost:8080/api'

  constructor(private http: HttpClient) {}

  public getAllImages(): Observable<Imagem[]> {
    return this.http.get<Imagem[]>(`${this.urlAPI}/images/all-images`);
  }

  public getImageById(id: number) {
    return this.http.get<Imagem>(`${this.urlAPI}/images/imagem/${id}`);
  }

  public getKeywordsFromUrl(url: string) {
    return this.http.get<String[]>(`${this.urlAPI}/images/get-keywords?url=${url}`)
  }

  private addImagemToDatabase(url: String): Observable<number> {
    return this.http.post<number>(`${this.urlAPI}/images/cadastrar?url=${url}`, {});
  }

  public addImagemComCategorias(url: String, newKeywords: String[], trKeywords: String[]): Observable<boolean> {
    return this.http.post<string>(`${this.urlAPI}/images/imagem-dto`, new ImagemDto(url, trKeywords, newKeywords)).pipe(
      map((response: string) => response.includes("sucesso")),
      catchError(() => {
        return of(false);
      })
    );
  }



}
