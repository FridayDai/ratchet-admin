var flight = require('flight');
var withDialog = require('../common/withDialog');
var withForm = require('../common/withForm');

var MODELS = {
    CREATE: 'CREATE',
    EDIT: 'EDIT'
};

var TOOLTYPE = {
    2: 'OUTCOME',
    4: 'VOICE'
};

var SUCCESS_EVENT_NAMES = {
    CREATE: 'createDefinedToolSuccess',
    EDIT: 'editDefinedToolSuccess'
};

function definedToolFormDialog() {
    this.attributes({
        submitBtnSelector: '.create-btn',

        dialogTitleSelector: '.modal-title',

        toolTypeFieldSelector: '.defined-tool-type',
        toolTypeOutcomeSelector: '#outcome-tool-type',
        toolTypeVoiceSelector: '#voice-tool-type',
        defaultDueTimeDayFieldSelector: '[name="defaultDueTimeDay"]',
        defaultDueTimeHourFieldSelector: '[name="defaultDueTimeHour"]',
        defaultExpireTimeDayFieldSelector: '[name="defaultExpireTimeDay"]',
        defaultExpireTimeHourFieldSelector: '[name="defaultExpireTimeHour"]',
        reminderFieldSelector: '#defined-tool-reminder',

        createUrl: '/clients/{0}/treatments/{1}/tools',
        editUrl: '/clients/{0}/treatments/{1}/tools/{2}'
    });

    this.initValidation = function () {
        $.validator.addMethod('reminderCheck', function (value) {
            var regexp = /^\s*\d+\s*((,\s*\d+\s*)*)$/;

            return regexp.test(value);
        }, "Invalid reminder format.");

        $.validator.addMethod('defaultDueTimeLessReminderCheck', function () {
            var regexp = /\d+/g;
            var dueDayVal = $('#add-defined-tool-modal').find('[name="defaultDueTimeDay"]').val();
            dueDayVal = parseInt(dueDayVal, 10);

            var reminderVal = $('#defined-tool-reminder').val();

            var reminders = _.map(reminderVal.match(regexp), function (val) {
                return parseInt(val, 10);
            });

            if (reminders) {
                return _.max(reminders) <= dueDayVal - 1;
            } else {
                return false;
            }

        }, "The day of max reminder should be less 1 day than default due time.");

        $.validator.addMethod('expireTimeLessDueTimeCheck', function () {
            var $modal = $('#add-defined-tool-modal');

            var expireDay = $modal.find('[name="defaultExpireTimeDay"]').val();
            var expireHour = $modal.find('[name="defaultExpireTimeHour"]').val();

            if(!expireDay && !expireHour) {
                return true;
            }

            var dueDayVal = parseInt($modal.find('[name="defaultDueTimeDay"]').val(), 10);
            var dueHourVal = parseInt($modal.find('[name="defaultDueTimeHour"]').val(), 10);
            var expireDayVal = parseInt($modal.find('[name="defaultExpireTimeDay"]').val(), 10);
            var expireHourVal = parseInt($modal.find('[name="defaultExpireTimeHour"]').val(), 10);

            return (dueDayVal * 24 + dueHourVal) <= (expireDayVal * 24 + expireHourVal);
        }, "The expire time should be equal or greater than due time.");

        this.formEl.validate({
            rules: {
                reminder: {
                    reminderCheck: true,
                    defaultDueTimeLessReminderCheck: true
                },
                defaultExpireTimeDay: {
                    expireTimeLessDueTimeCheck: true
                },
                defaultExpireTimeHour: {
                    expireTimeLessDueTimeCheck: true
                }
            },
            groups: {
                defaultExpireTime: "defaultExpireTimeDay defaultExpireTimeHour"
            }
        });
    };

    this.onDefaultDayChange = function () {
        this.select('reminderFieldSelector').valid();
        this.select('defaultExpireTimeDayFieldSelector').valid();
    };

    this.modal = 'CREATE';

    this.onCreateModal = function (e, data) {
        this.model = MODELS.CREATE;
        this.changeTollSelect(data.toolType);

        this.setCreateModal(data);

        this.showDialog();
    };

    this.changeTollSelect = function (type) {
        if(type === "VOICE") {
            this.select('toolTypeOutcomeSelector').hide().attr('name', 'idle');
            this.select('toolTypeVoiceSelector').show().attr('name', 'id');
        } else{
            this.select('toolTypeOutcomeSelector').show().attr('name', 'id');
            this.select('toolTypeVoiceSelector').hide().attr('name', 'idle');
        }
    };

    this.setCreateModal = function (data) {
        this.clearForm();

        this.formEl.data({
            loadingText: 'Loading...'
        });

        this.select('dialogTitleSelector').text('Add Tool');
        this.select('toolTypeFieldSelector').removeAttr('disabled');
        this.select('submitBtnSelector').text('Create');
        this.formEl.attr('action', this.attr.createUrl.format(data.clientId, data.treatmentId));
    };

    this.onEditModal = function (e, data) {
        this.model = MODELS.EDIT;
        this.changeTollSelect(TOOLTYPE[data.tool.type]);

        this.setEditModal(data);

        this.showDialog();
    };

    this.setEditModal = function (data) {
        this.clearForm();

        this.formEl.data({
            loadingText: 'Updating...'
        });

        this.select('dialogTitleSelector').text('Edit Tool');
        this.select('toolTypeFieldSelector').attr('disabled', 'disabled');
        this.select('submitBtnSelector').text('Update');
        this.formEl.attr('action', this.attr.editUrl.format(data.clientId, data.treatmentId, data.tool.id));

        this.setEditModalFieldValue(data.tool);
    };

    this.setEditModalFieldValue = function (tool) {

        if(TOOLTYPE[tool.type] === "VOICE") {
            this.select('toolTypeVoiceSelector').val(tool.basetoolId);
        } else {
            this.select('toolTypeOutcomeSelector').val(tool.basetoolId);
        }

        this.select('defaultDueTimeDayFieldSelector').val(tool.defaultDueTimeDay);
        this.select('defaultDueTimeHourFieldSelector').val(tool.defaultDueTimeHour);
        this.select('defaultExpireTimeDayFieldSelector').val(tool.defaultExpireTimeDay);
        this.select('defaultExpireTimeHourFieldSelector').val(tool.defaultExpireTimeHour);
        this.select('reminderFieldSelector').val(tool.reminder);
    };

    this.onFormSuccess = function (e, data) {
        this.trigger(SUCCESS_EVENT_NAMES[this.model], data);

        this.hideDialog();
    };

    this.after('initialize', function () {
        this.on(document, 'showCreateDefinedToolFormDialog', this.onCreateModal);
        this.on(document, 'showEditDefinedToolFormDialog', this.onEditModal);

        this.on('formSuccess', this.onFormSuccess);

        this.on('change', {
            'defaultDueTimeDayFieldSelector': this.onDefaultDayChange
        });
    });
}

module.exports = flight.component(withDialog, withForm, definedToolFormDialog);
