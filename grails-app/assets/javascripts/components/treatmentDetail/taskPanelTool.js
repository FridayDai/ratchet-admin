var flight = require('flight');
var treatmentStorage = require('./treatmentStorage');

function toolPoolPanelTool() {
    this.attributes({
        addTaskBtnSelector: '#add-item-btn',
        absoluteEventTypeSelector: '.surgery-time-required'
    });

    this.onAddTaskBtnClick = function () {
        var $addButton = this.select('addTaskBtnSelector');

        if (!$addButton.hasClass('disabled')) {
            this.trigger('showCreateTaskFormDialog', {
                clientId: treatmentStorage.get('clientId'),
                treatmentId: treatmentStorage.get('treatmentId'),
                absoluteEventType: treatmentStorage.get('absoluteEventType')
            });
        }
    };

    this.after('initialize', function () {
        this.on('click', {
            addTaskBtnSelector: this.onAddTaskBtnClick
        });
    });
}

module.exports = flight.component(toolPoolPanelTool);
