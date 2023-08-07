import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Imagem } from '../models/imagem';

@Injectable({
  providedIn: 'root'
})
export class BackendService{

  private url = 'http://localhost:8080/api'

  constructor(private http: HttpClient) {}

  public getAllImages(): Observable<Imagem[]> {
    return this.http.get<Imagem[]>(`${this.url}/images/all-images`);
  }

  public getImageById(id: number) {
    return this.http.get<Imagem>(`${this.url}/images/imagem/${id}`);
  }

  public getKeywordsFromUrl(url: string) {
    return this.http.get<String[]>(`${this.url}/images/get-keywords?url=${url}`)
  }

newImagemId!: number;
public addImagemWithTags(url: string, newKeywords: String[], keywordsFromMetadataToRemove: String[]) {
  this.addImagemToDatabase(url).subscribe(
    (id: number) => {
      this.newImagemId = id;


        newKeywords.forEach((categoria: String) => {
          console.log(categoria + " " + this.newImagemId)
        });

        keywordsFromMetadataToRemove.forEach((categoriaToRemove: String) => {
          console.log(categoriaToRemove + " " + this.newImagemId);
        });
    }
  );
}


  private addImagemToDatabase(url: string) {
    return this.http.post<number>(`${this.url}/images/cadastrar?url=${url}`, {});
  }

  private addCategoriaToImagem(imagemId: number, categoria: String) {
    return new Promise<boolean>((resolve, reject) => {
      this.http.post<boolean>(`${this.url}/images/add-categoria?id=${imagemId}&categoria=${categoria}`, {})
        .subscribe(
          (data) => {
            resolve(true);
          },
          (error) => {
            console.error(error);
            reject(false);
          }
        );
    });
  }

  private removeCategoriaFromImagem(imagemId: number, categoria: String) {
    this.http.delete(
      `${this.url}/keywords/deletar?id=${imagemId}&categoria=${categoria}`, {})
  }


}
