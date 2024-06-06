import packageInfo from '../../package.json';

export const VERSION = packageInfo.version;

export const environment = {
  production: false,
  backendApi: 'http://localhost:8090/elfa-vz-verifier-scs',
  // 300000 milliseconds = 5 minutes
  pollingTimeoutTime: 300000,
  // 4000 milliseconds = 4 seconds
  pollingInterval: 4000,
  // 900000 milliseconds = 15 minutes
  navigationDelay: 900000,
  // 7 days
  cookieValidity: 7
};
