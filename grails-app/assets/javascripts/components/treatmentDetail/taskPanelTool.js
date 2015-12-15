var flight = require('flight');
var treatmentStorage = require('./treatmentStorage');

function toolPoolPanelTool() {
    this.attributes({
        addTaskBtnSelector: '#add-item-btn'
    });

    this.onAddTaskBtnClick = function () {
        var $addButton = this.select('addTaskBtnSelector');

        if (!$addButton.hasClass('disabled')) {
            this.trigger('showCreateTaskFormDialog', {
                clientId: treatmentStorage.get('clientId'),
                treatmentId: treatmentStorage.get('treatmentId')
            });
        }
    };

    this.onEditTreatmentSuccess = function (e, data) {
        var $addButton = this.select('addTaskBtnSelector');

        if (data.surgeryTimeRequire === 'Yes') {
            $addButton.removeClass('disabled');
        } else {
            if (!$addButton.hasClass('disabled')) {
                $addButton.addClass('disabled');
            }
        }
    };

    this.after('initialize', function () {
        this.on('click', {
            addTaskBtnSelector: this.onAddTaskBtnClick
        });

        this.on(document, 'editTreatmentSuccess', this.onEditTreatmentSuccess);
    });
}

module.exports = flight.component(toolPoolPanelTool);
