import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app.component';
import { DebtListComponent } from './debt-list/debt-list.component';
import { DebtFormComponent } from './debt-form/debt-form.component';
import { DebtServiceService } from './service/debt-service.service';

@NgModule({
  declarations: [AppComponent, DebtListComponent, DebtFormComponent],
  imports: [BrowserModule, AppRoutingModule, HttpClientModule, FormsModule],
  providers: [DebtServiceService],
  bootstrap: [AppComponent],
})
export class AppModule {}
