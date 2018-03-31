var merge = require('webpack-merge')
var prodEnv = require('./prod.env')

module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
  // BASE_API: '"http://192.168.2.123:8080"'
  BASE_API: '"http://www.motooling.com:8080/motooling"'
  // BASE_API: '"http://www.motooling.com:8080/motooling_test"'
  // BASE_API: '"http://192.168.2.114:8080"'npm
  // BASE_API: '"http://192.168.137.253:8080"'
  //BASE_API: '"http://192.168.1.20:8080"'
  // BASE_API: '"http://192.168.2.172:8080"'
})

