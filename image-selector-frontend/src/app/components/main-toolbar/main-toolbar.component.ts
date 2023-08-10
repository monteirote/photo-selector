import { Component, EventEmitter, Output } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { PopupCadastroComponent } from '../popup-cadastro/popup-cadastro.component';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-main-toolbar',
  templateUrl: './main-toolbar.component.html',
  styleUrls: ['./main-toolbar.component.css'],
})
export class MainToolbarComponent {

  @Output() navSidebar = new EventEmitter<void>();

  toggleSidebar() {
    this.navSidebar.emit();
  }

  searchFormulario: FormGroup;
  textToSearch: String = '';

  constructor(public dialog: MatDialog, private formBuilder: FormBuilder, private route: Router) {
    this.searchFormulario = this.formBuilder.group({
      fotoUrl: ['', [Validators.required]],
    });
  }

  openCadastroPopup() {
    const dialogRef = this.dialog.open(PopupCadastroComponent);

    dialogRef.componentInstance.close.subscribe(() => {
      this.dialog.closeAll();
    });
  }

  onSearch() {
    const searchText = (
      document.getElementsByName('search')[0] as HTMLInputElement
    ).value;
    if (searchText == '') return;
    this.route.navigateByUrl("/buscar/" + searchText);
    (document.getElementsByName('search')[0] as HTMLInputElement).value = "";
    // (document.getElementsByName('search')[0] as HTMLInputElement).blur();
  }


}
