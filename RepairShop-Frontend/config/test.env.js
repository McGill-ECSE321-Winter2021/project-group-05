var merge = require('webpack-merge')
var devEnv = require('./dev.env')

//testing
module.exports = merge(devEnv, {
  NODE_ENV: '"testing"'
})
