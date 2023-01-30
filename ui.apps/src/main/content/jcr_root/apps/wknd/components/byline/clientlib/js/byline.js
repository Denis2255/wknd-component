(function ($, $document) {

    var testvalue = false;



    $(document).on("click", "button[trackingelement='check']", function() {
        check_text();
        if (testvalue == true) {
            alert("INVALID VALUE");
        } else {
            alert("VALID VALUE");
        }
    });

    function check_text() {
        var el = $(".text");
        var pattern = /[0-9]/;
        var textvalue = el.val();
        if (pattern.test(textvalue)) {
            testvalue = true;
        } else {
            testvalue = false;
        }
    };

})($ , $(document));