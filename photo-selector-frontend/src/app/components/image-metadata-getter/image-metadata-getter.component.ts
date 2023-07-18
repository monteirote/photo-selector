import { parse, ExifTool } from 'exifr';
import { Component, ElementRef, ViewChild } from '@angular/core';
import { exiftool } from 'exiftool-vendored';

@Component({
  selector: 'app-image-metadata-getter',
  templateUrl: './image-metadata-getter.component.html',
  styleUrls: ['./image-metadata-getter.component.css']
})
export class ImageMetadataGetterComponent {
  metadata: any;

  onFileChange(event: any) {
    const file = event.target.files[0];
    if (file) {
      parse(file)
        .then((metadata) => {
          this.metadata = metadata;
          console.log(this.metadata);

          // Metadados a serem adicionados (exemplo: nome do autor)
          const metadataToAdd = {
            Author: 'Seu Nome',
          };

          // Chame o método write para adicionar os metadados
          exiftool.write(file, metadataToAdd)
            .then(() => {
              console.log('Metadados adicionados com sucesso!');
            })
            .catch((error) => {
              console.error('Erro ao adicionar metadados:', error);
            });
        })
        .catch((error) => {
          console.error('Erro ao ler os metadados:', error);
        });
    }
  }
}
