require('datetimepicker');
var flight = require('flight');
var withFormDialog = require('../common/withFormDialog');

function debugScheduleFormDialog() {
    /* jshint validthis:true */

    flight.compose.mixin(this, [
        withFormDialog
    ]);

    this.attributes({
        submitBtnSelector: '.create-btn',
        lastDebugDateDiv: '#lastDebugDateTime',
        debugDateInput: '#debugDateTime'
    });

    this.fillLastDebugDate = function (event, data) {
        this.select('lastDebugDateDiv').text(data.timeForDebug);
    };

    this.formSuccessProcess = function () {
        this.hideDialog();
    };

    this._initDatePicker = function() {
        $(this.attr.debugDateInput).datetimepicker({
                format: "YYYY-MM-DD HH:mm:ss"
            }
        );
    };

    this.initMyForm = function() {
        this._initDatePicker();
    };

    this.after('initialize', function () {
        this.initMyForm();
        this.on('formSuccess', this.formSuccessProcess);
        this.on(document, 'showDateTimeFormDialog', this.fillLastDebugDate);
    });
}

module.exports = flight.component(withFormDialog, debugScheduleFormDialog);


