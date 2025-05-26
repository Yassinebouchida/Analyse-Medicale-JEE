import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AnalysisService } from '../services/analysis.service';
import jsPDF from 'jspdf';
import html2canvas from 'html2canvas';

import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatChipsModule } from '@angular/material/chips';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { ReactiveFormsModule } from '@angular/forms';


@Component({
  selector: 'app-home',
  standalone: true,
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
  imports: [
    FormsModule,
    ReactiveFormsModule,
    CommonModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatAutocompleteModule,
    MatChipsModule,
    MatButtonModule,
    MatIconModule
  ]
})
export class HomeComponent {
  form = {
    firstName: '',
    lastName: '',
    birthDate: '',
    gender: '',
    height: 0,
    weight: 0,
    symptomNames: [] as string[],
    biologicalResults: [{ markerName: '', value: 0 }]
  };

  currentSymptom = '';
  response: any = null;
  loading = false;

  markerSuggestions = ['CRP', 'Glucose', 'Hémoglobine', 'Globules blancs', 'Plaquettes'];
  symptomSuggestions = ['Fièvre', 'Toux', 'Maux de tête', 'Fatigue', 'Douleurs musculaires'];
  heightOptions = [145, 150, 155, 160, 165, 170, 175, 180, 185, 190, 195, 200];
  weightOptions = [45, 50, 55, 60, 65, 70, 75, 80, 90, 95, 100];

  constructor(private analysisService: AnalysisService) {
    const saved = localStorage.getItem('lastAnalysisResult');
    if (saved) this.response = JSON.parse(saved);
  }

  addSymptom(): void {
    const s = this.currentSymptom.trim();
    if (s && !this.form.symptomNames.includes(s)) {
      this.form.symptomNames.push(s);
      this.currentSymptom = '';
    }
  }

  removeSymptom(i: number): void {
    this.form.symptomNames.splice(i, 1);
  }

  addBiologicalResult(): void {
    this.form.biologicalResults.push({ markerName: '', value: 0 });
  }

  resetForm(): void {
    this.form = {
      firstName: '', lastName: '', birthDate: '', gender: '',
      height: 0, weight: 0,
      symptomNames: [], biologicalResults: [{ markerName: '', value: 0 }]
    };
    this.currentSymptom = '';
    this.response = null;
  }

  submitAnalysis(): void {
    this.loading = true;
    const cleanedMarkers = this.form.biologicalResults.filter(r => r.markerName.trim());
    const payload = { ...this.form, biologicalResults: cleanedMarkers };

    this.analysisService.createAnalysis(payload).subscribe({
      next: res => {
        this.response = { ...res, biologicalResults: cleanedMarkers };
        localStorage.setItem('lastAnalysisResult', JSON.stringify(this.response));
        this.loading = false;
      },
      error: err => {
        alert('Erreur API : ' + err.message);
        this.loading = false;
      }
    });
  }

  exportPdf(): void {
    const el = document.getElementById('pdf-content');
    if (!el) return;

    el.style.display = 'block';
    setTimeout(() => {
      html2canvas(el, { scale: 2 }).then(canvas => {
        const imgData = canvas.toDataURL('image/png');
        const pdf = new jsPDF({ orientation: 'p', unit: 'mm', format: 'a4' });
        const pdfW = pdf.internal.pageSize.getWidth();
        const pdfH = canvas.height * pdfW / canvas.width;
        pdf.addImage(imgData, 'PNG', 0, 0, pdfW, pdfH);
        pdf.save('rapport-analyse.pdf');
        el.style.display = 'none';
      }).catch(() => {
        alert('Erreur génération PDF');
        el.style.display = 'none';
      });
    }, 300);
  }
}
