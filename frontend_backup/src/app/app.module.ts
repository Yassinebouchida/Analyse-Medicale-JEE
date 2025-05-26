import { NgModule }          from '@angular/core';
import { BrowserModule }     from '@angular/platform-browser';
import { FormsModule,
  ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule }  from '@angular/common/http';

import { AppComponent }      from './app.component';
import { HomeComponent }     from './home/home.component';   // ✅ stand-alone
import { AnalysisService }   from './services/analysis.service';

@NgModule({
  declarations: [
    AppComponent                // ❌ HomeComponent n’est PLUS déclaré
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,

    // 👉 On « importe » explicitement le stand-alone
    HomeComponent
  ],
  providers: [AnalysisService],
  bootstrap: [AppComponent]
})
export class AppModule {}
