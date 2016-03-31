require('jValidate');
require('jForm');
var flight = require('flight');
var withServerError = require('../common/withServerError');

function withPrimitiveForm() {
    /* jshint validthis:true */

    flight.compose.mixin(this, [
        withServerError
    ]);

    this._setDefaultValidation = function () {
        $.validator.setDefaults({
            errorClass: 'error-help-block',
            errorPlacement: function(error, element) {
                var errorContainer = element.parent();
                var $form = element.closest('form');
                var validator = $form.data('validator');

                if (element.data('groupValidation')) {
                    var $groupPatient = element.closest('.' + validator.groups[element.attr('name')] + '-groups');
                    errorContainer = $groupPatient.parent();
                }

                $("<div class='error-container'></div>").appendTo(errorContainer).append(error);
            }
        });
    };

    this.attributes({
        formSelector: 'form'
    });

    this.initForm = function () {
        this.formEl = this.select('formSelector');

        if (_.isFunction(this.initValidation)) {
            this.initValidation();
        } else {
            this.formEl.validate();
        }

        this._setDefaultValidation();

        var componentValidations = this.formEl.data('componentRules');

        _.each(componentValidations, function (item) {
            if (item.element) {
                item.element.rules('add', item.rules);
            }
        });
    };

    this.onCheckConsistency = function (e) {

        if (this.formEl.valid()) {
            var $this = this.$node;
            var originValue = $this.find(this.attr.originSelector).val();
            var confirmValue = $this.find(this.attr.confirmSelector).val();

            if (originValue && confirmValue) {
                if (originValue !== confirmValue) {
                    e.preventDefault();
                    $this.find(this.attr.errorMsgSelector).text(this.attr.errorMsg);
                    return false;
                } else {
                    $this.submit();
                }
            } else {
                $this.submit();
            }

        }
    };

    this.after('initialize', function () {
        this.initForm();

        this.on('click', {
            'submitBtnSelector': this.onCheckConsistency
        });
    });
}

module.exports = withPrimitiveForm;
