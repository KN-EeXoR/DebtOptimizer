import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DebtListComponent } from './debt-list/debt-list.component';
import { DebtFormComponent } from './debt-form/debt-form.component';

const routes: Routes = [
  { path: 'debts', component: DebtListComponent },
  { path: 'addDebt', component: DebtFormComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
