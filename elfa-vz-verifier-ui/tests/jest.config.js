const { pathsToModuleNameMapper } = require("ts-jest");
const { compilerOptions } = require("../tsconfig");

module.exports = {
  roots: ["<rootDir>/src"],
  transform: {
    "^.+\\.tsx?$": "ts-jest",
  },
  preset: "jest-preset-angular",
  setupFilesAfterEnv: ["<rootDir>/tests/setupJest.ts"],
  globals: {
    "ts-jest": {
      tsconfig: "tsconfig.spec.json",
      diagnostics: {
        ignoreCodes: ["TS151001"],
      },
    },
  },
  moduleNameMapper: pathsToModuleNameMapper(compilerOptions.paths || {}, {
    prefix: "<rootDir>/",
    "^package.json$": "<rootDir>/package.json",
  }),
  transformIgnorePatterns: ["<rootDir>/node_modules/(?!(.*\\.mjs)|date-fns)"],
  coverageDirectory: "<rootDir>/coverage/sonarQube",
  testPathIgnorePatterns: ["/node_modules/", "/dist/"],
  testResultsProcessor: "jest-sonar-reporter",
  collectCoverage: true,
  collectCoverageFrom: ["<rootDir>/src/**/*.ts", "!<rootDir>/src/app/core/api/generated/**"],
};
