var webpack = require("webpack");
var CommonsChunkPlugin = require("webpack/lib/optimize/CommonsChunkPlugin");

var contextUrl = "grails-app/assets/javascripts";
var absoluteContext = __dirname + "/" + contextUrl;

module.exports = {
    devtool: 'source-map',
    context: contextUrl,
    entry: {
        clients: "./pages/clients.js",
        clientDetail: "./pages/clientDetail.js",
        profile: "./pages/profile.js",
        debug: "./pages/debug.js",
        announcements: "./pages/announcements.js",
        accounts:"./pages/accounts.js",
        treatmentDetail: "./pages/treatmentDetail.js",
        login: "./pages/login.js",
        passwordForget: "./pages/passwordForget.js",
        passwordReset: "./pages/passwordReset.js",
        accountActivate:"./pages/accountActivate.js",
        hl7Failures:"./pages/hl7/failures.js"
    },
    output: {
        path: absoluteContext,
        filename: "./dist/[name].bundle.js"
    },
    resolve: {
        root: absoluteContext,
        alias: {
            jquery: "bower_components/jquery/dist/jquery.js",
            lodash: "bower_components/lodash/lodash.js",
            flight: "bower_components/flight/index.js",
            bootstrap: "bower_components/bootstrap/dist/js/bootstrap.js",
            datepicker: "bower_components/bootstrap-datepicker/dist/js/bootstrap-datepicker",
            datetimepicker: "bower_components/eonasdan-bootstrap-datetimepicker/src/js/bootstrap-datetimepicker.js",
            jValidate: "bower_components/jquery-validation/dist/jquery.validate.js",
            jForm: "bower_components/jquery-form/jquery.form.js",
            dataTable: "bower_components/DataTables/media/js/jquery.dataTables.js",
            moment: "bower_components/moment/min/moment.min.js",
            momentTZ: "bower_components/moment-timezone/builds/moment-timezone-with-data.js",
            select2: "bower_components/select2/select2.js",
            tooltipster: "bower_components/tooltipster/js/jquery.tooltipster.js"
        }
    },
    module: {
        noParse: [
        ]
    },
    plugins: [
        new webpack.ProvidePlugin({
            $: "jquery",
            jQuery: "jquery",
            "window.jQuery": "jquery",
            "root.jQuery": "jquery",
            _: "lodash",
            moment: 'moment'
        }),
        new CommonsChunkPlugin("./dist/commons.chunk.js")
    ]
};
