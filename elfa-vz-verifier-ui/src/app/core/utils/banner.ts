import {ObIBanner} from '@oblique/oblique/lib/utilities.model';

export function banner(environment: string | undefined): ObIBanner {
  const banners: {[key: string]: ObIBanner} = {
    LOCAL: {
      text: 'LOCAL',
      color: '#fff',
      bgColor: '#00813a'
    },
    DEV: {
      text: 'DEV',
      color: '#171717',
      bgColor: '#ffd700'
    },
    REF: {
      text: 'REF',
      color: '#171717',
      bgColor: '#e75e00'
    },
    ABN: {
      text: 'ABN',
      color: '#fff',
      bgColor: '#b00020'
    },
    PROD: {
      text: ''
    }
  };
  const env: string = environment ?? 'unknown';
  return banners[env] ?? banners.PROD;
}
