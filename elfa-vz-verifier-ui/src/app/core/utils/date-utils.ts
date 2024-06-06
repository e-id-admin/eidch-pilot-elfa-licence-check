import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class DateUtils {
  static parseDate(dateString: string): Date {
    if (dateString) {
      const [day, month, year] = dateString.split('.');
      return new Date(parseInt(year), parseInt(month) - 1, parseInt(day));
    }
    return null;
  }
}
