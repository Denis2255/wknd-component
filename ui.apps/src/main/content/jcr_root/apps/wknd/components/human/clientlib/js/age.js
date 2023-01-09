

(function($, Coral) {
    "use strict";

registry.register("foundation.validation.validator", {
    selector: "[data-validation=age-validation]",
    validate: function(element) {
        let el = $(element);
        let pattern=/[^[1-9][0-9]?$|^100$]/;
        let value=el.val();
        if(pattern.test(value)){
            return "Please add age 1-100";
        }

    }
});

})(jQuery, Coral);