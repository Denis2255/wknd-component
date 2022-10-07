(function ($, Coral) {
    "use strict";
    console.log("Clientslib loaded");
    var registry = $(window).adaptTo("foundation-registry");
    registry.register("foundation.validation.validator", {
        selector: "[data-validation=geeks-multifield]",
        validate: function (element) {
            var el = $(element);
            let min = el.data("min-items");
            let max = el.data("max-items");
            let items = el.children("coral-multifield-item").length;
            console.log("{}:{}:{}", max, min, items);
            if (items > max) {
                return "You can add only" + max + ".You can trying to add" + items
            }
            if (items < min) {
                return "You add" + min
            }
        }
    });

})(jQuery, Coral);