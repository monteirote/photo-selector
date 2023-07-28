import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { FirstComponentComponent } from './components/first-component/first-component.component';
import { NgxMasonryModule } from 'ngx-masonry';
import { MainToolbarComponent } from './components/main-toolbar/main-toolbar.component';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatSidenavModule } from '@angular/material/sidenav';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { GaleriaComponent } from './components/galeria/galeria.component';
import { OverlayModule } from '@angular/cdk/overlay';


@NgModule({
  declarations: [
    AppComponent,
    FirstComponentComponent,
    MainToolbarComponent,
    GaleriaComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    NgxMasonryModule,
    MatToolbarModule,
    MatButtonModule,
    MatIconModule,
    MatSidenavModule,
    NoopAnimationsModule,
    OverlayModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
