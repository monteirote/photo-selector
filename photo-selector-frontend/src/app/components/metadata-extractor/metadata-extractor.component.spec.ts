import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MetadataExtractorComponent } from './metadata-extractor.component';

describe('MetadataExtractorComponent', () => {
  let component: MetadataExtractorComponent;
  let fixture: ComponentFixture<MetadataExtractorComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MetadataExtractorComponent]
    });
    fixture = TestBed.createComponent(MetadataExtractorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
