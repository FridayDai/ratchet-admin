var flight = require('flight');
var withDialog = require('../common/withDialog');
var withForm = require('../common/withForm');
var Utility = require('../../utils/utility');

var MODELS = {
    CREATE: 'CREATE',
    EDIT: 'EDIT'
};

var TOOLTYPE = {
    1: 'BASIC',
    2: 'OUTCOME',
    4: 'VOICE',
    5: 'USER',
    6: 'RAPT'
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
        toolTypeBasicSelector: '#basic-tool-type',
        toolTypeRaptSelector: '#rapt-tool-type',
        toolTypeUserSelector: '#user-tool-type',
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

            var expireDayVal = parseInt($modal.find('[name="defaultExpireTimeDay"]').val(), 10);
            var expireHourVal = parseInt($modal.find('[name="defaultExpireTimeHour"]').val(), 10);

            if(!expireDayVal && !expireHourVal) {
                return true;
            }

            var dueDayVal = parseInt($modal.find('[name="defaultDueTimeDay"]').val(), 10);
            var dueHourVal = parseInt($modal.find('[name="defaultDueTimeHour"]').val(), 10);

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
        this.changeToolSelect(data.toolType);

        this.setCreateModal(data);

        this.showDialog();
    };

    this.changeToolSelect = function (type) {
        _.each(['BASIC', 'OUTCOME', 'VOICE','RAPT', 'USER'], function (item) {
            var $item = this.select('toolType{0}Selector'.format(Utility.capitalize(item)));

            if (type === item) {
                $item.show().prop('disabled', false);
            } else {
                $item.hide().prop('disabled', true);
            }
        }, this);
    };

    this.setCreateModal = function (data) {
        this.clearForm();

        this.formEl.data({
            loadingText: 'Loading...'
        });

        this.select('dialogTitleSelector').text('Add Tool');
        this.select('submitBtnSelector').text('Create');
        this.formEl.attr('action', this.attr.createUrl.format(data.clientId, data.treatmentId));
    };

    this.onEditModal = function (e, data) {
        this.model = MODELS.EDIT;
        this.changeToolSelect(TOOLTYPE[data.tool.type]);

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
        switch (TOOLTYPE[tool.type]) {
            case 'VOICE':
                this.select('toolTypeVoiceSelector').val(tool.basetoolId);
                break;
            case 'OUTCOME':
                this.select('toolTypeOutcomeSelector').val(tool.basetoolId);
                break;
            case 'USER':
                this.select('toolTypeUserSelector').val(tool.basetoolId);
                break;
            case 'RAPT':
                this.select('toolTypeRaptSelector').val(tool.basetoolId);
                break;
            case 'BASIC':
                this.select('toolTypeBasicSelector').val(tool.basetoolId);
                break;
            default:
                break;
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
