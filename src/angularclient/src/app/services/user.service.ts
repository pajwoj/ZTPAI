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

  addUser(user: User) {
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    return this.http.post<User>(this.url + "/register", user, {headers});
  }
}