require('jquery');
require('bootstrap');
require('../components/common/initSetup');

var tfa = require('../components/login/twoFactorAuthentication');

tfa.attachTo('.auth-form');
