import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { BackendService } from 'src/app/backend/backend.service';
import { Imagem } from 'src/app/models/imagem';

@Component({
  selector: 'app-galeria',
  templateUrl: './galeria.component.html',
  styleUrls: ['./galeria.component.css']
})
export class GaleriaComponent implements OnInit {

  images!: Imagem[];

  constructor(private service: BackendService, private router: Router) {}

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
