import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Station} from "../models/station";

@Injectable({
  providedIn: 'root'
})
export class StationService {
  private url: string;
  constructor(private http: HttpClient) {
    this.url = 'http://localhost:8080/api/stations'
  }

  public findAll(): Observable<Station[]> {
    return this.http.get<Station[]>(this.url + "/all");
  }
}
