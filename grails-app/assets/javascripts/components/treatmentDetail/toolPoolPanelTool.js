var flight = require('flight');
var treatmentStorage = require('./treatmentStorage');

function toolPoolPanelTool() {
    this.attributes({
        definedBtnSelector: '.defined'
    });

    this.onDefinedBtnClick = function (e) {
        var type = $(e.target).data('type');

        this.trigger('showCreateDefinedToolFormDialog', {
            clientId: treatmentStorage.get('clientId'),
            treatmentId: treatmentStorage.get('treatmentId'),
            toolType: type
        });
    };

    this.after('initialize', function () {
        this.on('click', {
            definedBtnSelector: this.onDefinedBtnClick
        });
    });
}

module.exports = flight.component(toolPoolPanelTool);
