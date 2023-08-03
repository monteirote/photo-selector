import { Component, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatStepper } from '@angular/material/stepper';
import { BackendService } from 'src/app/backend/backend.service';

@Component({
  selector: 'app-popup-cadastro',
  templateUrl: './popup-cadastro.component.html',
  styleUrls: ['./popup-cadastro.component.css']
})
export class PopupCadastroComponent {
  @ViewChild(MatStepper) stepper!: MatStepper;
  imageFormulario: FormGroup;

  constructor(private formBuilder: FormBuilder, private service: BackendService) {
    this.imageFormulario = this.formBuilder.group({
      fotoUrl: ['', [Validators.required, ]],
    });
  }

  urlImage: string = '';

  isUrlValid!: boolean;

  keywordsPadrao!: String[];
  newKeywords: String[] = [];
  keywordIsLoading: boolean = true;
  newKeyword: string = '';

  textoDoInputTags: string = '';

  isValidURL(url: string): boolean {
    try {
      new URL(url);
      if (this.checkURL(url)) {
        return true;
      }
      return false;
    } catch (error) {
      return false;
    }
  }

  get fotoUrl() {
    return this.imageFormulario.get('fotoUrl');
  }

  onSubmit() {

  }

  toSecondStep(newUrl: string) {
    this.urlImage = newUrl;
    if ((this.urlImage.trim() == '') || !this.isValidURL(this.urlImage)) {
      this.isUrlValid = false;
      return;
    }
    this.stepper.next();
    this.getKeywords(this.urlImage)
  }

  checkURL(url: string) {
    return(url.match(/\.(jpeg|jpg|gif|png)$/) != null);
  }

  getKeywords(url: string) {
    this.service.getKeywordsFromUrl(url).subscribe(
      (result) => {
        this.keywordsPadrao = result;
        console.log(result);
        this.keywordIsLoading = false;
      },
      (error) => {
        return error;
      }
    )
  }

  onInputTag() {
    const categoria = (document.getElementsByName('newTag')[0] as HTMLInputElement).value;
    this.addNewTag(categoria);
    this.newKeyword = '';
  }

  checagemPadrao: String[] = [];
  checagemNovas: String[] = [];
  private addNewTag(tag: string) {

    this.keywordsPadrao.forEach((keyword: String) => {
      this.checagemPadrao.push(keyword.toLowerCase());
    })
    this.newKeywords.forEach((keyword: String) => {
      this.checagemNovas.push(keyword.toLowerCase());
    })

    if (this.checagemPadrao.includes(tag.toLowerCase()) || this.checagemNovas.includes(tag.toLowerCase())) return;
    this.keywordsPadrao.push(tag);
    this.newKeywords.push(tag);
  }

  onRemoveTag(tag: String) {
    // fazer com que a string escolhida seja removida dos dois arrays
  }


}


