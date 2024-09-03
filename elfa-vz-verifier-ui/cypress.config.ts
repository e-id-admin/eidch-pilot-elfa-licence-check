import {defineConfig} from 'cypress';

export default defineConfig({
  e2e: {
    setupNodeEvents(on, config) {
      // implement node event listeners here
    },
    baseUrl: 'http://localhost:4210',
    chromeWebSecurity: false,
    specPattern: 'cypress/e2e-local/*.cy.ts'
  }
});
