// Import this named export into your test file:
import {UseCase} from '@app/_models';

export const dummyUseCases = [new UseCase('1', 'Title1', 'Desc1', 1, []), new UseCase('2', 'Title2', 'Desc2', 2, [])];
export const mockGetUseCases = jest.fn().mockReturnValue(dummyUseCases);
export const mockStartVerificationProcess = jest.fn();
export const mockGetVerificationProcess = jest.fn();

const mock = jest.fn().mockImplementation(() => {
  return {
    getUseCases: mockGetUseCases,
    startVerificationProcess: mockStartVerificationProcess,
    getVerificationProcess: mockGetVerificationProcess
  };
});

export default mock;
