import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface LoginResponse {
  username: string;
  authorities: string[];
  token: string;
  idUser: number; // Asegúrate de que tu backend siempre devuelva este campo, o puede ser null
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:9000/api/auth';
  private username: string = '';

  constructor(private http: HttpClient) {}

  login(credentials: { username: string; password: string }): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/login`, credentials);
  }

  register(user: any): Observable<any> {
    const token = this.getToken();
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json',
    });
    return this.http.post<any>(`${this.apiUrl}/register`, user, { headers });
  }

  setSession(response: LoginResponse): void {
    if (response && response.token) {
      sessionStorage.setItem('token', response.token);
      sessionStorage.setItem('username', response.username);
      sessionStorage.setItem('roles', JSON.stringify(response.authorities));
      if (response.idUser != null) {
        sessionStorage.setItem('userId', response.idUser.toString());
      } else {
        console.error("LoginResponse no contiene idUser; no se podrá guardar el identificador del usuario.");
      }
      this.username = response.username;
    } else {
      console.error("El objeto LoginResponse es nulo o no contiene token.");
    }
  }

  getToken(): string | null {
    return sessionStorage.getItem('token');
  }

  getRoles(): string[] {
    try {
      return JSON.parse(sessionStorage.getItem('roles') || '[]');
    } catch {
      return [];
    }
  }
  isArtista(): boolean {
    return this.getRoles().includes('ROLE_ARTISTA');
  }
  
  getUserRole(): string | null {
    const roles = this.getRoles();
    return roles.length > 0 ? roles[0] : null;
  }
  getUserId(): number | null {
    const userId = sessionStorage.getItem('userId');
    return userId ? Number(userId) : null;
  }

  getUsername(): string {
    return sessionStorage.getItem('username') || this.username;
  }

  isAdmin(): boolean {
    return this.getRoles().includes('ADMIN');
  }

  isUser(): boolean {
    return this.getRoles().includes('USER');
  }

  logout(): void {
    sessionStorage.clear();
    this.username = '';
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }
}
