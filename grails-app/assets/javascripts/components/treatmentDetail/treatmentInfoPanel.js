var flight = require('flight');
var withPanel = require('../common/withPanel');
var treatmentStorage = require('./treatmentStorage');
var Utility = require('../../utils/utility');

function treatmentInfoPanel() {
    /* jshint validthis:true */

    this.attributes({
        treatmentTitleSelector: '.title strong',
        templateTitleSelector: '.template-title .text',
        absoluteEventTypeSelector: '.surgery-time-required .text',
        descriptionSelector: '.description',
        statusSelector: '.status .text',
        autoArchiveSelector: '.auto-archive',
        autoArchiveTextSelector: '.auto-archive span',

        editTreatmentBtnSelector: 'button[data-target="#edit-treatment-modal"]',
        closeTreatmentBtnSelector: 'button[data-target="#close-treatment-modal"]'
    });

    this.onShowEditTreatmentDialog = function () {
        var data = {
            treatmentTitle: this.get('treatmentTitle'),
            templateTitle: this.get('templateTitle'),
            absoluteEventType: this.get('absoluteEventType'),
            description: this.get('description')
        };

        var archiveTime = this.select('autoArchiveSelector').data('autoArchive');

        if (archiveTime) {
            var separateTime = Utility.getTimeInterval(archiveTime);

            data.autoArchive = {
                time: archiveTime,
                week: separateTime.weeks,
                day: separateTime.days
            };
        }
        this.trigger('showEditTreatmentFormDialog', data);
    };

    this.onShowCloseTreatmentDialog = function () {
        this.trigger('showCloseTreatmentDialog', {
            clientId: this.$node.data('clientId'),
            treatmentId: this.$node.data('treatmentId')
        });
    };

    this.onEditTreatmentSuccess = function (e, data) {
        this.select('treatmentTitleSelector').text(data.treatmentTitle);
        this.select('templateTitleSelector').text(data.templateTitle);
        this.select('absoluteEventTypeSelector').text(data.absoluteEventType);
        this.select('descriptionSelector').text(data.description);
        treatmentStorage.set('absoluteEventType', data.absoluteEventType);
        this.select('autoArchiveSelector').data('autoArchive', data.autoArchive ? data.autoArchive.time : 0);

        var absoluteEventType = data.absoluteEventType.toLowerCase();
        this.select('autoArchiveTextSelector').text(
            data.autoArchive && data.autoArchive.time ?
                ('{0}W {1}D after ' + absoluteEventType ).format(data.autoArchive.week, data.autoArchive.day) :
                'NA'
        );

    };

    this.onCloseTreatmentSuccess = function () {
        this.select('statusSelector').text('Closed');
    };

    this.setValue4TreatmentStore = function () {
        treatmentStorage
            .set('clientId', this.$node.data('clientId'))
            .set('treatmentId', this.$node.data('treatmentId'))
            .set('absoluteEventType', this.$node.data('absoluteEventType'));
    };

    this.after('initialize', function () {
        this.setValue4TreatmentStore();

        this.on(document, 'editTreatmentSuccess', this.onEditTreatmentSuccess);
        this.on(document, 'closeTreatmentSuccess', this.onCloseTreatmentSuccess);

        this.on('click', {
            'editTreatmentBtnSelector': this.onShowEditTreatmentDialog,
            'closeTreatmentBtnSelector': this.onShowCloseTreatmentDialog
        });
    });
}



module.exports = flight.component(withPanel, treatmentInfoPanel);
