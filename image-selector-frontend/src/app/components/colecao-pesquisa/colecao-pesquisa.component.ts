import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-colecao-pesquisa',
  templateUrl: './colecao-pesquisa.component.html',
  styleUrls: ['./colecao-pesquisa.component.css']
})
export class ColecaoPesquisaComponent {

  constructor(private route: Router) {}

  textToSearch: String = '';

  onInputSearch() {
    const searchText = (
      document.getElementsByName('search')[0] as HTMLInputElement
    ).value;
    if (searchText == '') return;
    this.route.navigateByUrl("/buscar/" + searchText);
    (document.getElementsByName('search')[0] as HTMLInputElement).value = "";
    (document.getElementsByName('search')[0] as HTMLInputElement).blur();
  }


}
