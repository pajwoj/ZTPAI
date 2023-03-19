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

  public logout(user: User) {
    const headers = new HttpHeaders();

    headers.append('Content-Type', 'application/json');

    return this.http.post(this.url + "/logout", user, {responseType: "text", withCredentials: true, headers});
  }

  public getUser(email: String) {
    console.log(this.url + "/user?email=" + email);
    return this.http.get(this.url + "/user?email=" + email);
  }
}
