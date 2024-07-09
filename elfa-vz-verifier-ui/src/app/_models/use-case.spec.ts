import {UseCase} from './use-case';

describe('UseCase', () => {
  it('should create an instance', () => {
    expect(new UseCase(null, null, null, null, null)).toBeTruthy();
  });
});
