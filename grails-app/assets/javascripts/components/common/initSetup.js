(function() {
    /**
     * global ajax set up
     */
    $.ajaxSetup({
        beforeSend: function () {

        },
        complete: function () {

        },
        success: function () {

        },
        global: true,
        error: function (jqXHR) {
            if (jqXHR.status === 404) {
            } else if (jqXHR.status === 403 || jqXHR.status === 401) {
                alert('Permission denied!');
                window.location.href = '/login';
            } else if (jqXHR.status === 0) {
            }
            else {
            }
        }
    });

    /**
     * string format
     * **/
    String.prototype.format = function () {
        var str = this;
        if (arguments.length === 0) {
            return str;
        }

        for (var i = 0; i < arguments.length; i++) {
            var re = new RegExp('\\{' + i + '\\}', 'gm');
            if (arguments[i] !== undefined || arguments[i] !== null) {
                str = str.replace(re, arguments[i]);
            } else {
                str = str.replace(re, '');
            }
        }
        return str;
    };

    // Add serializeObject function to jQuery. Fetching and serialize all form key and value as json format.
    $.fn.serializeObject = function()
    {
        var o = {};
        var a = this.serializeArray();
        $.each(a, function() {
            if (o[this.name] !== undefined) {
                if (!o[this.name].push) {
                    o[this.name] = [o[this.name]];
                }
                o[this.name].push(this.value || '');
            } else {
                o[this.name] = this.value || '';
            }
        });
        return o;
    };
})();
