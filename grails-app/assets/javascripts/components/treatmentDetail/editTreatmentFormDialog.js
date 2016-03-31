var flight = require('flight');
var withDialog = require('../common/withDialog');
var withForm = require('../common/withForm');

function getAutoArchiveTime(week, day) {
    return (parseInt(week, 10) * 7 + parseInt(day, 10)) * 24 * 3600000;
}

function editTreatmentFormDialog() {
    /* jshint validthis:true */

    this.attributes({
        submitBtnSelector: '.update-btn',

        treatmentTitleFieldSelector: '#edit-treatment-title',
        templateTitleFieldSelector: '#edit-treatment-tmpTitle',
        surgeryTimeRequiredFieldSelector: '#edit-treatment-surgeryTimeRequired',
        descriptionFieldSelector: '#edit-treatment-description',
        archiveWeekSelector: '[name="archiveWeek"]',
        archiveDaySelector: '[name="archiveDay"]'
    });

    this.onShow = function (e, data) {
        this.select('treatmentTitleFieldSelector').val(data.treatmentTitle);
        this.select('templateTitleFieldSelector').val(data.templateTitle);
        this.select('surgeryTimeRequiredFieldSelector').val(data.surgeryTimeRequire === 'Yes' ? 'true' : 'false');
        this.select('descriptionFieldSelector').val(data.description);

        if (data.autoArchive) {
            this.select('archiveWeekSelector').val(data.autoArchive.week);
            this.select('archiveDaySelector').val(data.autoArchive.day);
        }

        if (data.surgeryTimeRequire === 'Yes') {
            this.select('archiveWeekSelector').prop('disabled', false);
            this.select('archiveDaySelector').prop('disabled', false);
        } else {
            this.select('archiveWeekSelector').val(0).prop('disabled', true);
            this.select('archiveDaySelector').val(0).prop('disabled', true);
        }
    };

    this.onSurgeryTimeRequiredChange = function () {
        var required = this.select('surgeryTimeRequiredFieldSelector').val();

        if (required === 'false') {
            this.select('archiveWeekSelector').val(0).prop('disabled', true);
            this.select('archiveDaySelector').val(0).prop('disabled', true);
        } else {
            this.select('archiveWeekSelector').prop('disabled', false);
            this.select('archiveDaySelector').prop('disabled', false);
        }
    };

    this.onFormSuccess = function (e, data) {
        var result = {
            treatmentTitle: data.title,
            templateTitle: data.tmpTitle,
            surgeryTimeRequire: data.surgeryTimeRequired === 'true' ? 'Yes' : 'No',
            description: data.description
        };

        if (data.archiveDay || data.archiveWeek) {
            result.autoArchive = {
                week: data.archiveWeek,
                day: data.archiveDay,
                time: getAutoArchiveTime(data.archiveWeek, data.archiveDay)
            };
        }

        this.trigger('editTreatmentSuccess', result);

        this.hideDialog();
    };

    this.after('initialize', function () {
        this.on(document, 'showEditTreatmentFormDialog', this.onShow);

        this.on('formSuccess', this.onFormSuccess);

        this.on('change', {
            surgeryTimeRequiredFieldSelector: this.onSurgeryTimeRequiredChange
        });
    });
}

module.exports = flight.component(withDialog, withForm, editTreatmentFormDialog);
