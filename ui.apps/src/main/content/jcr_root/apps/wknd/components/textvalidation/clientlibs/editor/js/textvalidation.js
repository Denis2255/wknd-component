/* global jQuery, Coral */
(function($, Coral) {
    "use strict";


    var registry = $(window).adaptTo("foundation-registry");

    registry.register("foundation.validation.validator", {
        selector: "[data-validation=text-validation]",
        validate: function(element) {
            let el = $(element);
            let pattern=/[0-9a-z]/;
            let value=el.val();
            if(pattern.test(value)){
               return "Please add only Uper Case Letters in Text";
            }

        }
    });


   
})(jQuery, Coral);
