var merge = require('webpack-merge')
var prodEnv = require('./prod.env')

module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
  // BASE_API: '"http://192.168.2.123:8080"'
  BASE_API: '"http://www.motooling.com:8080/motooling_test"'
  // BASE_API: '"http://localhost:8080"'

})

