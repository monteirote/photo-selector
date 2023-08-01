import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { BackendService } from 'src/app/backend/backend.service';
import { Imagem } from 'src/app/models/imagem';

@Component({
  selector: 'app-imagem-expandida',
  templateUrl: './imagem-expandida.component.html',
  styleUrls: ['./imagem-expandida.component.css']
})
export class ImagemExpandidaComponent implements OnInit {

  constructor(private backendService: BackendService, private route: ActivatedRoute) {}

  imageId!: any;
  imagemExibida!: Imagem;
  keywordsImagem!: String[];
  isLoading: boolean = true;

  ngOnInit(): void {

    this.imageId = this.route.snapshot.paramMap.get('id');

    this.backendService.getImageById(this.imageId).subscribe(
      (response: Imagem) => {
        this.imagemExibida = response;
        this.keywordsImagem = response.allKeywords;
        this.isLoading = false;
        console.log(this.imagemExibida);
      },
      (error) => {
        console.log(error)
        return error;
      }
    )
  }



}
