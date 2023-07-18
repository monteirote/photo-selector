import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ImageMetadataGetterComponent } from './image-metadata-getter.component';

describe('ImageMetadataGetterComponent', () => {
  let component: ImageMetadataGetterComponent;
  let fixture: ComponentFixture<ImageMetadataGetterComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ImageMetadataGetterComponent]
    });
    fixture = TestBed.createComponent(ImageMetadataGetterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
