var flight = require('flight');
var withForm = require('../common/withForm');
var withDialog = require('../common/withDialog');
var Constants = require('../../constants/AdminPortalConstants');

function addTreatmentFormDialog () {
    /* jshint validthis:true */

    this.attributes({
        submitBtnSelector: '.create-btn',
        absoluteEventTypeSelector: '#AbsoluteEventType',
        eventTypeSelector: '#eventType',
        archiveWeekSelector: '[name=archiveWeek]',
        archiveDaySelector: '[name=archiveDay]',
        archiveBlockSelector: '.auto-archive'
    });

    this.onFormSuccess = function (e, data) {
        this.trigger('createTreatmentSuccess', data);

        this.hideDialog();
    };

    this.onAbsoluteEventTypeChange = function () {
        var typeVal = this.select('absoluteEventTypeSelector').val();

        this.select('eventTypeSelector').text(typeVal.toLowerCase());

        if (typeVal === Constants.ABSOLUTE_TYPE_NONE) {
            this.select('archiveWeekSelector').val(0).prop('disabled', true);
            this.select('archiveDaySelector').val(0).prop('disabled', true);
            this.select('archiveBlockSelector').hide();
        } else {
            this.select('archiveBlockSelector').show();
            this.select('archiveWeekSelector').prop('disabled', false);
            this.select('archiveDaySelector').prop('disabled', false);
        }
    };

    this.after('initialize', function () {
        this.on('formSuccess', this.onFormSuccess);

        this.on('change', {
            absoluteEventTypeSelector: this.onAbsoluteEventTypeChange
        });
    });
}

module.exports = flight.component(withForm, withDialog, addTreatmentFormDialog);
