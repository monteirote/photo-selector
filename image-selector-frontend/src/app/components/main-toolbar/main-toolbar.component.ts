import { Component, EventEmitter, Output } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { PopupCadastroComponent } from '../popup-cadastro/popup-cadastro.component';

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

  constructor(public dialog: MatDialog) {}

  openCadastroPopup() {
    const dialogRef = this.dialog.open(PopupCadastroComponent);

    dialogRef.componentInstance.close.subscribe(() => {
      this.dialog.closeAll();
    });
  }


}
