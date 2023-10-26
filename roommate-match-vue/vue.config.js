const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    port: 9000
  },
  chainWebpack: config => {
    config.plugin('html')
        .tap(args => {
          args[0].title = "ECUT-roommate-match";
          return args;
        })
  }
})
