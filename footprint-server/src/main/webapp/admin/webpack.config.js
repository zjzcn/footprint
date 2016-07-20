var webpack = require('webpack');
var ignoreFiles = new webpack.IgnorePlugin(/.+\.(jpg|gif|jpeg|png)$/);
module.exports = {
  entry: ['webpack/hot/dev-server', './src/main.js'],
  output: {
    path: './dist',
    publicPath: '/dist/',
    filename: 'build.js'
  },
  module: {
    loaders: [
      {test: /\.html$/, loader: 'html'},
      {test : /\.vue$/, loader : 'vue'},
      {test: /\.(png|jpg)$/, loader: 'url-loader?limit=8192'}
    ]
  },
  devtool: 'source-map'
  //plugins: [ignoreFiles]
}