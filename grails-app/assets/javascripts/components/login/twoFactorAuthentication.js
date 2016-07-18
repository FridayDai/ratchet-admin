var flight = require('flight');

function twoFactorAuthentication() {
    this.attributes({
        otpInputFieldSelector: '#otp'
    });

    this.filterOnlyNumber = function (){
        var val = this.select('otpInputFieldSelector').val();
        var reg = /\D/g;
        var num = val.replace(reg, '');

        this.select('otpInputFieldSelector').val(num);

    };

    this.after('initialize', function() {
        this.on('input', {otpInputFieldSelector: this.filterOnlyNumber});
    });
}

module.exports = flight.component(twoFactorAuthentication);
