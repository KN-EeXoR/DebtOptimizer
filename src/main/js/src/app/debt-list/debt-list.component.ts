import { Component, OnInit } from '@angular/core';
import { Debt } from '../model/loss';
import { DebtServiceService } from '../service/debt-service.service';

@Component({
  selector: 'app-debt-list',
  templateUrl: './debt-list.component.html',
  styleUrls: ['./debt-list.component.css']
})
export class DebtListComponent implements OnInit {

  debts : Debt[] = [];

  constructor(private debtService: DebtServiceService) { }

  ngOnInit() {
    this.debtService.getDebts().subscribe((data: Debt[]) => {
      this.debts = data;
    });
  }
}
