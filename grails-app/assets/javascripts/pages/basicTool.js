require('jquery');
require('bootstrap');
require('../components/common/initSetup');


var BasicToolFormDialog = require('../components/basicTool/basicToolFormDialog'),
    BasicToolToolbar = require('../components/basicTool/basicToolToolbar'),
    BasicToolTable = require('../components/basicTool/basicToolTable');

BasicToolTable.attachTo('#basic-tool-table');
BasicToolFormDialog.attachTo('#basic-tool-modal');
BasicToolToolbar.attachTo('#basic-tool-tool-bar');
