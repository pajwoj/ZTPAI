import { Injectable } from '@angular/core';
import {User} from "../models/user";
import {HttpClient, HttpHeaders} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private url: string;
  constructor(private http: HttpClient) {
    this.url = 'http://localhost:8080/api/users'
  }

  public addUser(user: User) {
    const headers = new HttpHeaders();

    headers.append('Content-Type', 'application/json');

    return this.http.post(this.url + "/register", user, {responseType: "text", headers});
  }
}
