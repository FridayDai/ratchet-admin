var flight = require('flight');

var withServerError = require('../../common/withServerError');

function FailuresTable() {
    this.attributes({
        retryButtonSelector: 'button'
    });

    this.onRetryButtonClicked = function (e) {
        var $target = $(e.target);
        var jobId = $target.closest('tr').find('.job-id').text();

        $.ajax('/hl7/error/{0}/reprocess'.format(jobId))
            .done(function () {
                $target.replaceWith('Processing...');
            })
            .fail(function (resp) {
                if (resp.responseJSON
                    && resp.responseJSON.error
                    && resp.responseJSON.error.errorMessage) {
                    alert(resp.responseJSON.error.errorMessage);
                } else {
                    alert('Bad Request!');
                }
            });
    };

    this.after('initialize', function () {
        this.on('click', {
            retryButtonSelector: this.onRetryButtonClicked
        });
    });
}

module.exports = flight.component(withServerError, FailuresTable);
