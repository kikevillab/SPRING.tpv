/**
  * @author Sergio Banegas Cortijo
  * Github: https://github.com/sergiobanegas 
*/
import { Injectable } from '@angular/core';

@Injectable()
export class PDFService {

  open(base64PDF: string): void {
    let pdfAsDataUri: string = `data:application/pdf;base64,${base64PDF}`;
    window.open(pdfAsDataUri);
  }

}