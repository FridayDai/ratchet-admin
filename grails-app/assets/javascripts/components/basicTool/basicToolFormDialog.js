var flight = require('flight');
var withFormDialog = require('../common/withFormDialog');

var MODEL = {
    NEW: 'new',
    EDIT: 'edit'
};

function addBasicToolFormDialog () {
    /* jshint validthis:true */

    this.attributes({
        submitBtnSelector: '.create-btn',
        modalTitleSelector: '.modal-title',

        testingCheckboxSelector: '#isTesting',
        titleFieldSelector: '#title',
        descriptionFieldSelector: '#description',
        questionnaireTitleFieldSelector: '#questionnaireTitle',
        questionnaireContentFieldSelector: '#questionnaireContent',

        createUrlUrl: '/basic_tool',
        editUrlUrl: '/basic_tool/{0}'
    });

    this.prepareShow = function (event, data) {
        if (data) {
            this.setEditModel(data);
        } else {
            this.setNewModel();
        }

        this.showDialog();
    };

    this.setEditModel = function (data) {
        this.model = MODEL.EDIT;

        this.select('modalTitleSelector').text('Edit Basic Tool');

        this.select('submitBtnSelector').text('Update');

        this.formEl.attr('action', this.attr.editUrlUrl.format(data.id));

        this.select('testingCheckboxSelector').prop('checked', data.isTesting);
        this.select('titleFieldSelector').val(data.title);
        this.select('descriptionFieldSelector').val(data.description);
        this.select('questionnaireTitleFieldSelector').val(data.basicToolTitle);
        this.select('questionnaireContentFieldSelector').val(data.basicToolContent);
    };

    this.setNewModel = function () {
        this.model = MODEL.NEW;

        this.select('modalTitleSelector').text('New Basic Tool');

        this.select('submitBtnSelector').text('Create');

        this.formEl.attr('action', this.attr.createUrlUrl);
    };

    this.onFormSuccess = function (e, data) {
        this.trigger('createBasicToolSuccess', data);
        this.hideDialog();
    };

    this.after('initialize', function () {
        this.on('formSuccess', this.onFormSuccess);
        this.on(document, 'showEditBasicToolFormDialog', this.prepareShow);
        this.on(document, 'showNewBasicToolDialog', this.prepareShow);
    });
}

module.exports = flight.component(withFormDialog, addBasicToolFormDialog);
