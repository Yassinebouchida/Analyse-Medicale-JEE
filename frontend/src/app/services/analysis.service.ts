import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AnalysisService {
    private api = 'http://localhost:8080/api/analysis';

    constructor(private http: HttpClient) {}

    createAnalysis(body: any): Observable<any> {
        return this.http.post<any>(this.api, body);
    }
}
