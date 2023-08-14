import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
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

  public addImagemComCategorias(url: String, newKeywords: String[], trKeywords: String[]): Observable<boolean> {
    return this.http.post<boolean>(`${this.urlAPI}/images/imagem-dto`, new ImagemDto(url, trKeywords, newKeywords));
  }

  public getImagesByTag(tag: String) {
    return this.http.get<Imagem[]>(`${this.urlAPI}/keywords/exibir/${tag}`);
  }

  public getAllCategorias() {
    return this.http.get<String[]>(`${this.urlAPI}/keywords`);
  }

  public getImagesByTaglist(tags: String[]) {
    let urlFormatada: string = `${this.urlAPI}/images/search-with-tags?`;
    tags.forEach((tag, index) => {

      if (index > 0) {
        urlFormatada += '&';
      }

      urlFormatada += `tag=${tag}`;
      return this.http.get(urlFormatada);
    });
  }

  public addTagToImagem(imagemId: number, tag: String) {
    return this.http.post(`${this.urlAPI}/images/add-categoria?id=${imagemId}&categoria=${tag}`, {})
  }





}
