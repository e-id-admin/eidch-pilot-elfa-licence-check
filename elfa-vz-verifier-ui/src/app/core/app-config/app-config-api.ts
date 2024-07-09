import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

export interface AppConfig {
  environment: string;
  version: string;
}

@Injectable({
  providedIn: 'root'
})
export class AppConfigApi {
  constructor(private http: HttpClient) {}

  getAppConfig(): Observable<AppConfig> {
    return this.http.get<AppConfig>('/api/app-config');
  }
}
