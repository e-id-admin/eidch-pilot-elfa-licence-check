module.exports = {
  $schema: "http://json.schemastore.org/prettierrc",
  printWidth: 120,
  overrides: [
    {
      files: "*.ts",
      options: {
        semi: true,
        singleQuote: true,
        bracketSpacing: false,
        useTabs: false,
        tabWidth: 2,
        trailingComma: "none",
        arrowParens: "avoid",
      },
    },
    {
      files: "*.html",
      options: {
        parser: "angular",
      },
    },
  ],
};
