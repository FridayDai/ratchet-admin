var flight = require('flight');
var withDialog = require('../common/withDialog');
var withForm = require('../common/withForm');

var MODELS = {
    CREATE: 'CREATE',
    EDIT: 'EDIT'
};

var SUCCESS_EVENT_NAMES = {
    CREATE: 'createTaskSuccess',
    EDIT: 'editTaskSuccess'
};

function taskFormDialog() {
    this.attributes({
        submitBtnSelector: '.create-btn',

        dialogTitleSelector: '.modal-title',

        toolTypeHiddenFieldSelector: '[name="toolId"][type="hidden"]',
        toolTypeFieldSelector: '#add-item-tool-id',
        sendTimeDirectionFieldSelector: '[name="sendTimeDirection"]',
        sendTimeWeeksFieldSelector: '[name="sendTimeWeeks"]',
        sendTimeDaysFieldSelector: '[name="sendTimeDays"]',
        sendTimeHoursFieldSelector: '[name="sendTimeHours"]',
        sendTimeMinutesFieldSelector: '[name="sendTimeMinutes"]',

        createUrl: '/clients/{0}/treatments/{1}/tasks',
        editUrl: '/clients/{0}/treatments/{1}/tasks/{2}'
    });

    this.onDirectionFieldChanged = function (e) {
        var targetEl = $(e.target);

        this.toggleSendTimeNumberFiled(targetEl.val() !== '0');
    };

    this.modal = 'CREATE';

    this.onCreateModal = function (e, data) {
        this.model = MODELS.CREATE;

        this.setCreateModal(data);

        this.showDialog();
    };

    this.setCreateModal = function (data) {
        this.clearForm();

        this.formEl.data({
            loadingText: 'Loading...'
        });

        this.select('dialogTitleSelector').text('Add Task');
        this.select('toolTypeHiddenFieldSelector').attr('disabled', 'disabled');
        this.select('toolTypeFieldSelector').removeAttr('disabled');
        this.select('submitBtnSelector').text('Create');
        this.formEl.attr('action', this.attr.createUrl.format(data.clientId, data.treatmentId));

        this.select('sendTimeDirectionFieldSelector').val('0');

        this.checkSurgeryTimeRequire(data);
        this.toggleSendTimeNumberFiled(false);
    };

    this.onEditModal = function (e, data) {
        this.model = MODELS.EDIT;

        this.setEditModal(data);

        this.showDialog();
    };

    this.setEditModal = function (data) {
        this.clearForm();

        this.formEl.data({
            loadingText: 'Updating...'
        });

        this.select('dialogTitleSelector').text('Edit Task');
        this.select('toolTypeHiddenFieldSelector').removeAttr('disabled');
        this.select('toolTypeFieldSelector').attr('disabled', 'disabled');
        this.select('submitBtnSelector').text('Update');
        this.formEl.attr('action', this.attr.editUrl.format(data.clientId, data.treatmentId, data.task.id));

        this.checkSurgeryTimeRequire(data);
        this.setEditModalFieldValue(data.task);
    };

    this.setEditModalFieldValue = function (task) {
        this.select('toolTypeHiddenFieldSelector').val(task.toolId);
        this.select('toolTypeFieldSelector').val(task.toolId);
        this.select('sendTimeDirectionFieldSelector').val(task.sendTimeDirection);

        if (task.sendTimeDirection !== 0) {
            this.select('sendTimeWeeksFieldSelector').val(task.sendTimeWeeks);
            this.select('sendTimeDaysFieldSelector').val(task.sendTimeDays);
            this.select('sendTimeHoursFieldSelector').val(task.sendTimeHours);
            this.select('sendTimeMinutesFieldSelector').val(task.sendTimeMinutes);
        } else {
            this.toggleSendTimeNumberFiled(false);
        }
    };

    this.toggleSendTimeNumberFiled = function (isEnable) {
        if (isEnable) {
            this.select('sendTimeWeeksFieldSelector').removeAttr('disabled');
            this.select('sendTimeDaysFieldSelector').removeAttr('disabled');
            this.select('sendTimeHoursFieldSelector').removeAttr('disabled');
            this.select('sendTimeMinutesFieldSelector').removeAttr('disabled');
        } else {
            this.select('sendTimeWeeksFieldSelector').val(0).attr('disabled', 'disabled');
            this.select('sendTimeDaysFieldSelector').val(0).attr('disabled', 'disabled');
            this.select('sendTimeHoursFieldSelector').val(0).attr('disabled', 'disabled');
            this.select('sendTimeMinutesFieldSelector').val(0).attr('disabled', 'disabled');
        }
    };

    this.checkSurgeryTimeRequire = function (data) {
        var $sendTime = this.select('sendTimeDirectionFieldSelector');
        if (data.surgeryTimeRequire === 'Yes') {
            $sendTime.removeClass('disabled').removeAttr('disabled');
        } else {
            if (!$sendTime.hasClass('disabled')) {
                $sendTime.addClass('disabled').attr('disabled', 'disabled');
            }
        }
    };

    this.onFormSuccess = function (e, data) {
        this.trigger(SUCCESS_EVENT_NAMES[this.model], data);

        this.hideDialog();
    };

    this.onCreateDefinedToolSuccess = function (e, data) {
        var optionStr = '<option value="{0}">{1}</option>'.format(data.id, data.title);

        this.select('toolTypeFieldSelector')
            .append(optionStr);
    };

    this.onDeleteToolSuccess = function (e, data) {
        var selector = '[value="{0}"]'.format(data.toolId);

        this.select('toolTypeFieldSelector')
            .find(selector)
            .remove();
    };

    this.after('initialize', function () {
        this.on(document, 'showCreateTaskFormDialog', this.onCreateModal);
        this.on(document, 'showEditTaskFormDialog', this.onEditModal);

        this.on(document, 'createDefinedToolSuccess', this.onCreateDefinedToolSuccess);
        this.on(document, 'deleteToolSuccess', this.onDeleteToolSuccess);

        this.on('formSuccess', this.onFormSuccess);

        this.on('change', {
            'sendTimeDirectionFieldSelector': this.onDirectionFieldChanged
        });
    });
}

module.exports = flight.component(withDialog, withForm, taskFormDialog);
