import { Component, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatStepper } from '@angular/material/stepper';

@Component({
  selector: 'app-popup-cadastro',
  templateUrl: './popup-cadastro.component.html',
  styleUrls: ['./popup-cadastro.component.css']
})
export class PopupCadastroComponent {
  @ViewChild(MatStepper) stepper!: MatStepper;
  imageFormulario: FormGroup;

  constructor(private formBuilder: FormBuilder) {
    this.imageFormulario = this.formBuilder.group({
      fotoUrl: ['', [Validators.required, ]],
    });
  }

  urlImage: string = '';
  isUrlValid!: boolean;

  isValidURL(url: string): boolean {
    try {
      new URL(url);
      return true;
    } catch (error) {
      return false;
    }
  }

  get fotoUrl() {
    return this.imageFormulario.get('fotoUrl');
  }

  onSubmit() {
    // Lógica para o envio do formulário, se necessário
  }

  onNextStep(newUrl: string) {
    this.urlImage = newUrl;
    if ((this.urlImage.trim() == '') || !this.isValidURL(this.urlImage)) {
      this.isUrlValid = false;
      return;
    }
    this.stepper.next();
  }
}
