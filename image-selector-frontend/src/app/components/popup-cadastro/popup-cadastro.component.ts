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
  keywordsList!: String[];
  keywordIsLoading: boolean = true;

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
        this.keywordsList = result;
        console.log(result);
        this.keywordIsLoading = false;
      },
      (error) => {
        return error;
      }
    )
  }


}


