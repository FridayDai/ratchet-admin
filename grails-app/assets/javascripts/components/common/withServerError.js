function withServerError() {
    /* jshint validthis:true */

    this.attributes({
        serverErrorSelector: '.rc-server-error'
    });

    this.serverErrorHandler = function (reqObj) {
        var Msg = JSON.parse(reqObj.responseText);
        //var Msg = JSON.parse(reqObj.responseText).error.errorMessage;
        if (reqObj.status === 403) {
            alert('Permission denied! Please try to refresh page!');
        } else if(reqObj.status === 400) {
            alert(Msg.error.errorMessage);
            this.showServerError(Msg.error.errorMessage);
        }else {
            this.showServerError(Msg.error.errorMessage);
        }
    };

    this.showServerError = function (msg) {
        this.select('serverErrorSelector').show().text(msg);
    };
}

module.exports = withServerError;
