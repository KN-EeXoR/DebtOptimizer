import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Debt } from '../model/loss';
import { Observable } from 'rxjs';

@Injectable()
export class DebtServiceService {
  private debtsUrl: string;

  constructor(private http: HttpClient) {
    this.debtsUrl = 'http://localhost:8080/users';
  }

  public getDebts(): Observable<Debt[]> {
    return this.http.get<Debt[]>(this.debtsUrl);
  }

  public postDebt(debt: Debt) {
    return this.http.post<Debt>(this.debtsUrl, debt);
  }
}
