import { Component } from '@angular/core';

declare const EXIF: any;

@Component({
  selector: 'app-metadata-extractor',
  templateUrl: './metadata-extractor.component.html',
  styleUrls: ['./metadata-extractor.component.css']
})
export class MetadataExtractorComponent {

  photoUrl: string = 'https://cdn-sites-images.46graus.com/files/home/2609/original_dea34cb8-35f7-4826-8397-489e75b67a7b.jpg';
  metadata: any;

  extractMetadata() {
    const img = new Image();
    img.onload = () => {
      this.metadata = EXIF.getAllTags(img);
    };
    img.src = this.photoUrl;
  }
}
