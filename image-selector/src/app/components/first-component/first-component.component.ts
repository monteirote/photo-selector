import { BackendService } from './../../backend/backend.service';
import { Component, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-first-component',
  templateUrl: './first-component.component.html',
  styleUrls: ['./first-component.component.css']
})
export class FirstComponentComponent implements OnInit {

  constructor(private service: BackendService, private sanitizer: DomSanitizer) {}

  images: any[] = [];

  ngOnInit(): void {
    this.getAllImages();
  }

  getAllImages() {
    this.service.getAllImages().subscribe(
      (data) => {
        this.images = data;
        console.log(this.images);
      },
      (error) => {
        console.log("erro", error)
      }
    )
  }

  getSafeUrl(url: string) {
    return this.sanitizer.bypassSecurityTrustUrl(url);
  }

}
