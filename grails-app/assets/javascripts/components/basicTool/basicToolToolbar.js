var flight = require('flight');

function BasicToolToolbar() {
    this.attributes({
        newBasicToolButtonSelector: '#add-basic-tool'
    });

    this.onNewBasicToolClicked = function () {
        this.trigger('showNewBasicToolDialog');
    };

    this.after('initialize', function () {
        this.on('click', {
            newBasicToolButtonSelector: this.onNewBasicToolClicked
        });
    });
}

module.exports = flight.component(BasicToolToolbar);
