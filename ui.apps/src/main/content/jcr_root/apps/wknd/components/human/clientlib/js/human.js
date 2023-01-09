
(function($, Coral) {
    "use strict";


    var registry = $(window).adaptTo("foundation-registry");

    registry.register("foundation.validation.validator", {
        selector: "[data-validation=text-validation-first-upper]",
        validate: function(element) {
            let el = $(element);
            let pattern=/^[a-z]/;
            let value=el.val();
            if(pattern.test(value)){
                return "Please add Upper Case for first letter in Text";
            }

        }
    });




})(jQuery, Coral);