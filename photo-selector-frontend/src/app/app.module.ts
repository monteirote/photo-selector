import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ImageMetadataGetterComponent } from './components/image-metadata-getter/image-metadata-getter.component';

@NgModule({
  declarations: [
    AppComponent,
    ImageMetadataGetterComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
