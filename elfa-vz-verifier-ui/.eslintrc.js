module.exports = {
  root: true,
  ignorePatterns: ["projects/**/*"],
  overrides: [
    {
      files: ["*.ts"],
      parserOptions: {
        project: ["tsconfig.json"],
        createDefaultProgram: true,
      },
      extends: [
        "plugin:@angular-eslint/recommended",
        "plugin:@angular-eslint/template/process-inline-templates",
        "plugin:prettier/recommended",
      ],
      rules: {
        "prettier/prettier": ["error", { endOfLine: "auto" }],
        "@angular-eslint/directive-selector": [
          "error",
          {
            type: "attribute",
            prefix: "app",
            style: "camelCase",
          },
        ],
        "@angular-eslint/component-selector": [
          "error",
          {
            type: "element",
            prefix: "app",
            style: "kebab-case",
          },
        ],
      },
    },
    {
      files: ["*.spec.ts", "**/__mocks__/*.ts"],
      parserOptions: {
        project: ["tsconfig.spec.json"],
        createDefaultProgram: true,
      },
      extends: [
        "plugin:@angular-eslint/recommended",
        "plugin:@angular-eslint/template/process-inline-templates",
        "plugin:prettier/recommended",
      ],
      rules: {
        "@angular-eslint/directive-selector": [
          "error",
          {
            type: "attribute",
            prefix: "app",
            style: "camelCase",
          },
        ],
        "@angular-eslint/component-selector": [
          "error",
          {
            type: "element",
            prefix: "app",
            style: "kebab-case",
          },
        ],
      },
    },
    {
      files: ["*.js"],
      parserOptions: {
        ecmaVersion: 2022,
      },
    },
    {
      files: ["*.html"],
      excludedFiles: ["*inline-template-*.component.html"],
      extends: ["plugin:@angular-eslint/template/recommended", "plugin:prettier/recommended"],
      rules: {
        "prettier/prettier": [
          "error",
          {
            parser: "angular",
            endOfLine: "auto",
          },
        ],
      },
    },
  ],
};
