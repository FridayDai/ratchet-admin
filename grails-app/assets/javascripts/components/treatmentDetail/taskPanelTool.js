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
                treatmentId: treatmentStorage.get('treatmentId'),
                surgeryTimeRequire: treatmentStorage.get('surgeryTimeRequire')
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
