import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { DebtServiceService } from '../service/debt-service.service';
import { Debt } from '../model/loss';

@Component({
  selector: 'app-debt-form',
  templateUrl: './debt-form.component.html',
  styleUrls: ['./debt-form.component.css'],
})
export class DebtFormComponent {
  debt: Debt;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private debtService: DebtServiceService
  ) {
    this.debt = new Debt();
  }

  onSubmit() {
    this.debtService
      .postDebt(this.debt)
      .subscribe((result) => this.gotoDebtList());
  }

  gotoDebtList() {
    this.router.navigate(['/debts']);
  }
}
