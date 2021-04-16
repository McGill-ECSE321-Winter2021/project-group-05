var merge = require('webpack-merge')
var prodEnv = require('./prod.env')
//development
module.exports = merge(prodEnv, {
  NODE_ENV: '"development"'
})
