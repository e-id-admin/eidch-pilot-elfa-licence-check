import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {StartVerificationResponse, UseCase, VerificationResponse} from '@app/_models';

@Injectable({
  providedIn: 'root'
})
export class VerifierService {
  constructor(private http: HttpClient) {}

  getUseCases(): Observable<UseCase[]> {
    return this.http.get<UseCase[]>('/api/v1/verifier/use-cases');
  }

  startVerificationProcess(useCaseId: string): Observable<StartVerificationResponse> {
    if (useCaseId === null || useCaseId === undefined) {
      throw new Error('Required parameter useCaseId was null or undefined when calling startVerificationProcess.');
    }
    return this.http.post<StartVerificationResponse>('/api/v1/verifier/verify', {
      useCaseId: useCaseId
    });
  }

  getVerificationProcess(id: string): Observable<VerificationResponse> {
    if (id === null || id === undefined) {
      throw new Error('Required parameter verificationId was null or undefined when calling startPolling.');
    }
    return this.http.get<VerificationResponse>('/api/v1/verifier/verify/' + encodeURIComponent(id));
  }
}
