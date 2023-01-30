
(function($) {

    var SELECTOR = "text-validation-first-upper",
        foundationReg = $(window).adaptTo("foundation-registry");

    foundationReg.register("foundation.validation.validator", {
        selector: "[data-validation='" + SELECTOR + "']",
        validate: function(el) {
            var pattern = /^[A-Z]/;
            var error_message = "Please add Upper Case for first letter in Text";
            var result = el.value.match(pattern);

            if (result === null) {
                return error_message;
            }
        }
    });

}(jQuery));
