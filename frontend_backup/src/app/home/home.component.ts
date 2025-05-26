import { Component } from '@angular/core';
import { AnalysisService } from '../services/analysis.service';
import jsPDF from 'jspdf';
import html2canvas from 'html2canvas';
import {FormsModule} from "@angular/forms";

@Component({
    selector: 'app-home',
    templateUrl: './home.component.html',
    imports: [
        FormsModule
    ],
    standalone: true,
    styleUrls: ['./home.component.scss']
})
export class HomeComponent {

    /** ---------------- Formulaire ---------------- */
    form = {
        firstName: '',
        lastName : '',
        birthDate: '',
        gender   : '',
        height   : 0,
        weight   : 0,
        symptomNames     : [] as string[],
        biologicalResults: [{ markerName: '', value: 0 }]
    };

    currentSymptom = '';
    response: any  = null;
    loading = false;

    /** ---------------- Suggestions (autocomplétions) ---------------- */
    markerSuggestions  = ['CRP', 'Glucose', 'Hémoglobine', 'Globules blancs', 'Plaquettes'];
    symptomSuggestions = ['Fièvre', 'Toux', 'Maux de tête', 'Fatigue', 'Douleurs musculaires'];
    heightOptions      = [145,150,155,160,165,170,175,180,185,190,195,200];
    weightOptions      = [45,50,55,60,65,70,75,80,90,95,100];

    constructor(private analysisService: AnalysisService) {
        /* Récupère le dernier résultat stocké localement (optionnel) */
        const saved = localStorage.getItem('lastAnalysisResult');
        if (saved) this.response = JSON.parse(saved);
    }

    /** ---------------- Gestion dynamique du formulaire ---------------- */
    addSymptom(): void {
        const s = this.currentSymptom.trim();
        if (s && !this.form.symptomNames.includes(s)) {
            this.form.symptomNames.push(s);
            this.currentSymptom = '';
        }
    }
    removeSymptom(i: number): void   { this.form.symptomNames.splice(i, 1); }
    addBiologicalResult(): void      { this.form.biologicalResults.push({ markerName: '', value: 0 }); }

    resetForm(): void {
        this.form = {
            firstName: '', lastName: '', birthDate: '', gender: '',
            height: 0, weight: 0,
            symptomNames: [], biologicalResults: [{ markerName: '', value: 0 }]
        };
        this.currentSymptom = '';
        this.response = null;
    }

    /** ---------------- Appel backend ---------------- */
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

    /** ---------------- Export PDF ---------------- */
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
