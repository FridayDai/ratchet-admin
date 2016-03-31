var flight = require('flight');

function ipTableToolbar() {
    /* jshint validthis:true */

    this.attributes({
        addButtonSelector: '#add-ip'
    });

    this.onAddIPButtonClicked = function () {
        this.trigger('showCreateIPFormDialog');
    };

    this.after('initialize', function () {
        this.on('click', {
            'addButtonSelector': this.onAddIPButtonClicked
        });
    });
}

module.exports = flight.component(ipTableToolbar);
