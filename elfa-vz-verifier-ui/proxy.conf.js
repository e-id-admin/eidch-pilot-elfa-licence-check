module.exports = {
  "/api/*": {
    target: "http://localhost:8090",
    changeOrigin: true,
    secure: false,
    logLevel: "debug",
  }
};
