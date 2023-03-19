import { Injectable } from '@angular/core';
import {User} from "../models/user";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private url: string;
  constructor(private http: HttpClient) {
    this.url = 'http://localhost:8080/api/users'
  }

  public register(user: User) {
    const headers = new HttpHeaders();

    headers.append('Content-Type', 'application/json');

    return this.http.post(this.url + "/register", user, {responseType: "text", withCredentials: true, headers});
  }

  public login(user: User) {
    const headers = new HttpHeaders();

    headers.append('Content-Type', 'application/json');

    return this.http.post(this.url + "/login", user, {responseType: "text", withCredentials: true, headers});
  }

  public logout() {
    const headers = new HttpHeaders();

    headers.append('Content-Type', 'application/json');

    return this.http.post(this.url + "/logout", "", {responseType: "text", withCredentials: true, headers});
  }
}
