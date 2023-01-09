(function($) {

    var REGEX_SELECTOR = "regex.validation",
        foundationReg = $(window).adaptTo("foundation-registry");

    foundationReg.register("foundation.validation.validator", {
        selector: "[data-validation='" + REGEX_SELECTOR + "']",
        validate: function(el) {
            var regex_pattern = /^$|^[0-9]+(\.[0-9]+){2}$/;
            var error_message = "The format must be 'X.X.X'.";
            var result = el.value.match(regex_pattern);

            if (result === null) {
                return error_message;
            }
        }
    });

}(jQuery));