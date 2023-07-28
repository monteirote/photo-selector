import { Component, EventEmitter, Output } from '@angular/core';

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


}
