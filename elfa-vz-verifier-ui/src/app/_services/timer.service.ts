import {Injectable} from '@angular/core';
import {Observable, Subscription, timer} from 'rxjs';
import {environment} from '@environments/environment';

@Injectable({
  providedIn: 'root'
})
export class TimerService {
  private timerSubscription: Subscription;
  private timerObservable: Observable<0>;

  startTimeoutTimer(): Observable<0> {
    // Start a timer (default: 5 minutes (300 seconds))
    this.timerObservable = timer(environment.pollingTimeoutTime);
    // Clear the interval subscription when the timer expires
    return this.timerObservable;
  }

  stopTimer() {
    this.timerSubscription?.unsubscribe();
    if (this.timerObservable) {
      this.timerObservable = null;
    }
  }

  subscribeToTimer(f: {next: () => void}) {
    this.timerSubscription = this.timerObservable.subscribe(f);
  }
}
