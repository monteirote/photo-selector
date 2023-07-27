import { BackendService } from './../../backend/backend.service';
import { Component, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { Imagem } from 'src/app/models/imagem';

@Component({
  selector: 'app-first-component',
  templateUrl: './first-component.component.html',
  styleUrls: ['./first-component.component.css']
})
export class FirstComponentComponent implements OnInit {

  constructor(private service: BackendService) {}

  images!: Imagem[];

  async ngOnInit(): Promise<void> {
    this.images = await this.getAllImages();
  }

  async getAllImages(): Promise<Imagem[]> {
    return new Promise<Imagem[]>((resolve, reject) => {
      this.service.getAllImages().subscribe(
        (data: Imagem[]) => {
          console.log(data);
          resolve(data);
        },
        (error) => {
          reject(error);
        }
      )
    })
  }




}
