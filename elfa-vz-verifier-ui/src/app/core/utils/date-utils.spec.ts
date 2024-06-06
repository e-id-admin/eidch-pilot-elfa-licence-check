import {DateUtils} from './date-utils';

describe('DateUtils class', () => {
  it('parses date string correctly', () => {
    const dateTestString = '26.09.2024';
    const expectedDate = new Date(2024, 8, 26); // Please note that JavaScript months start from 0.

    const result = DateUtils.parseDate(dateTestString);

    expect(result).toEqual(expectedDate);
  });

  it('returns null when date string is not defined', () => {
    const result = DateUtils.parseDate(undefined);

    expect(result).toBeNull();
  });

  it('returns null when date string is empty', () => {
    const result = DateUtils.parseDate('');

    expect(result).toBeNull();
  });
});
