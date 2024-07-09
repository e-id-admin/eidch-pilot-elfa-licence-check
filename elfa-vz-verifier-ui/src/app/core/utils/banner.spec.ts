import {ObIBanner} from '@oblique/oblique/lib/utilities.model';
import {banner} from './banner';

describe('banner', () => {
  it('returns local banner if environment is LOCAL', () => {
    const expectedBanner: ObIBanner = {
      text: 'LOCAL',
      color: '#fff',
      bgColor: '#00813a'
    };
    expect(banner('LOCAL')).toEqual(expectedBanner);
  });

  it('returns dev banner if environment is DEV', () => {
    const expectedBanner: ObIBanner = {
      text: 'DEV',
      color: '#171717',
      bgColor: '#ffd700'
    };
    expect(banner('DEV')).toEqual(expectedBanner);
  });

  it('returns ref banner if environment is REF', () => {
    const expectedBanner: ObIBanner = {
      text: 'REF',
      color: '#171717',
      bgColor: '#e75e00'
    };
    expect(banner('REF')).toEqual(expectedBanner);
  });

  it('returns abn banner if environment is ABN', () => {
    const expectedBanner: ObIBanner = {
      text: 'ABN',
      color: '#fff',
      bgColor: '#b00020'
    };
    expect(banner('ABN')).toEqual(expectedBanner);
  });

  it('returns prod banner if environment is PROD', () => {
    const expectedBanner: ObIBanner = {
      text: ''
    };
    expect(banner('PROD')).toEqual(expectedBanner);
  });

  it('returns prod banner if environment is unknown', () => {
    const expectedBanner: ObIBanner = {
      text: ''
    };
    expect(banner('unknown')).toEqual(expectedBanner);
  });

  it('returns prod banner if environment is unspecified', () => {
    const expectedBanner: ObIBanner = {
      text: ''
    };
    expect(banner(undefined)).toEqual(expectedBanner);
  });
});
