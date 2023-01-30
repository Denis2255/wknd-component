(function($) {

    var SELECTOR = "text-validation-age",
        foundationReg = $(window).adaptTo("foundation-registry");

    foundationReg.register("foundation.validation.validator", {
        selector: "[data-validation='" + SELECTOR + "']",
        validate: function(el) {
            var pattern = /^[1-9]$|^[1-9][0-9]$|^(100)$/;
            var error_message = "The format must be 1=100";
            var result = el.value.match(pattern);

            if (result === null) {
                return error_message;
            }
        }
    });

}(jQuery));
